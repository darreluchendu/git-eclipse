#include <list>
#include <functional>
#include <mutex>
#include <condition_variable>
#include <thread>
#include <optional>
#include <chrono>
#include <atomic>

#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <string>
#include <vector>
#include <unordered_map>
#include <unordered_set>
#include <list>
// Structs
struct work_queue {
  private: 
    std::list<std::string> q;
    std::mutex m;
    std:: condition_variable ready;
    bool done = false;

  public:
    std::optional<std::string> try_pop() {
      std::unique_lock<std::mutex> lock(m, std::try_to_lock);
      if (!lock || q.empty()) return {};
      auto f = q.front();
      q.pop_front();
      return f;
    }

    bool try_push(std::string f) {
      {
        std::unique_lock<std::mutex> lock(m, std::try_to_lock);
        if (!lock) return false;
        q.push_back(f);
      }
      ready.notify_one();
      return true;
    }

    std::optional<std::string> pop() {
      std::unique_lock<std::mutex> lock(m);
      ready.wait(lock, [this]{ return (!q.empty()) || done; });
      if (q.empty()) return {};
      auto f = q.front();
      q.pop_front();
      return f;
    }

    void push(std::string f) {
      {
        std::unique_lock<std::mutex> lock(m);
        q.push_back(f);
      }
      ready.notify_one();
    }

    void setDone() {
      {
        std::unique_lock<std::mutex> lock(m);
        done = true;
      }
      ready.notify_all();
    }
    std::list<std::string> get(){
      return q;
    }
};

struct hash_map {
  private: 
    std::unordered_map<std::string, std::list<std::string>> t;
    std::mutex m;
    std::condition_variable ready;

  public:
    void push(std::string key,std::list<std::string> items) {
      {
        std::unique_lock<std::mutex> lock(m);
       t.insert( { key,items } );
      }
      ready.notify_one();
    }
    std::unordered_map<std::string, std::list<std::string>> get(){
      return t;
    }
};

// struct task_system {
// private:
//   const int count = std::thread::hardware_concurrency();
//   std::vector<std::thread> threads;
//   work_queue q;

//   void run(int i) {
//     while (true) {
//       auto optional_f = q.pop();
//       if (!optional_f.has_value()) return;
//       auto f = optional_f.value();
//       f();
//     }
//   }

// public:
//   task_system() {
//     printf("Start task system with %d threads\n", count);
//     for (auto n = 0; n != count; n++) {
//       threads.emplace_back([this, n](){ run(n); });
//     }
//   }

//   ~task_system() {
//     q.setDone();
//     for (auto n = 0; n != count; n++) {
//       threads[n].join();
//     }
//   }

//   void async(std::function<void()> f) {
//     q.push(f);
//   }
// };


std::vector<std::string> dirs;
hash_map theTable;
work_queue workQ;

std::string dirName(const char * c_str) {
  std::string s = c_str; // s takes ownership of the string content by allocating memory for it
  if (s.back() != '/') { s += '/'; }
  return s;
}

std::pair<std::string, std::string> parseFile(const char* c_file) {
  std::string file = c_file;
  std::string::size_type pos = file.rfind('.');
  if (pos == std::string::npos) {
    return {file, ""};
  } else {
    return {file.substr(0, pos), file.substr(pos + 1)};
  }
}

// open file using the directory search path constructed in main()
static FILE *openFile(const char *file) {
  FILE *fd;
  for (unsigned int i = 0; i < dirs.size(); i++) {
    std::string path = dirs[i] + file;
    fd = fopen(path.c_str(), "r");
    if (fd != NULL)
      return fd; // return the first file that successfully opens
  }
  return NULL;
}

// process file, looking for #include "foo.h" lines
static void process(const char *file, std::list<std::string> *ll) {
  char buf[4096], name[4096];
  // 1. open the file
  FILE *fd = openFile(file);
  if (fd == NULL) {
    fprintf(stderr, "Error opening %s\n", file);
    exit(-1);
  }
  while (fgets(buf, sizeof(buf), fd) != NULL) {
    char *p = buf;
    // 2a. skip leading whitespace
    while (isspace((int)*p)) { p++; }
    // 2b. if match #include 
    if (strncmp(p, "#include", 8) != 0) { continue; }
    p += 8; // point to first character past #include
    // 2bi. skip leading whitespace
    while (isspace((int)*p)) { p++; }
    if (*p != '"') { continue; }
    // 2bii. next character is a "
    p++; // skip "
    // 2bii. collect remaining characters of file name
    char *q = name;
    while (*p != '\0') {
      if (*p == '"') { break; }
      *q++ = *p++;
    }
    *q = '\0';
    // 2bii. append file name to dependency list
    ll->push_back( {name} );
    // 2bii. if file name not already in table ...
    if (theTable.get().find(name) != theTable.get().end()) { continue; }
    // ... insert mapping from file name to empty list in table ...
    theTable.push(  name, {}  );
    // ... append file name to workQ
    workQ.push( name );
  }
  // 3. close file
  fclose(fd);
}

// iteratively print dependencies
static void printDependencies(std::unordered_set<std::string> *printed,
                              std::list<std::string> *toProcess,
                              FILE *fd) {
  if (!printed || !toProcess || !fd) return;

  // 1. while there is still a file in the toProcess list
  while ( toProcess->size() > 0 ) {
    // 2. fetch next file to process
    std::string name = toProcess->front();
    toProcess->pop_front();
    // 3. lookup file in the table, yielding list of dependencies
    std::list<std::string> *ll = &theTable.get()[name];
    // 4. iterate over dependencies
    for (auto iter = ll->begin(); iter != ll->end(); iter++) {
      // 4a. if filename is already in the printed table, continue
      if (printed->find(*iter) != printed->end()) { continue; }
      // 4b. print filename
      fprintf(fd, " %s", iter->c_str());
      // 4c. insert into printed
      printed->insert( *iter );
      // 4d. append to toProcess
      toProcess->push_back( *iter );
    }
  }
}

int main(int argc, char *argv[]) {
  // 1. look up CPATH in environment
  char *cpath = getenv("CPATH");

  // determine the number of -Idir arguments
  int i;
  for (i = 1; i < argc; i++) {
    if (strncmp(argv[i], "-I", 2) != 0)
      break;
  }
  int start = i;

  // 2. start assembling dirs vector
  dirs.push_back( dirName("./") ); // always search current directory first
  for (i = 1; i < start; i++) {
    dirs.push_back( dirName(argv[i] + 2 /* skip -I */) );
  }
  if (cpath != NULL) {
    std::string str( cpath );
    std::string::size_type last = 0;
    std::string::size_type next = 0;
    while((next = str.find(":", last)) != std::string::npos) {
      dirs.push_back( str.substr(last, next-last) );
      last = next + 1;
    }
    dirs.push_back( str.substr(last) );
  }
  // 2. finished assembling dirs vector

  // 3. for each file argument ...
  for (i = start; i < argc; i++) {
    std::pair<std::string, std::string> pair = parseFile(argv[i]);
    if (pair.second != "c" && pair.second != "y" && pair.second != "l") {
      fprintf(stderr, "Illegal extension: %s - must be .c, .y or .l\n",
              pair.second.c_str());
      return -1;
    }

    std::string obj = pair.first + ".o";

    // 3a. insert mapping from file.o to file.ext
    theTable.push( obj, { argv[i] } );
    
    // 3b. insert mapping from file.ext to empty list
    theTable.push(  argv[i], { }  );
    
    // 3c. append file.ext on workQ
    workQ.push( argv[i] );
  }

  // 4. for each file on the workQ
  while ( workQ.get().size() > 0 ) {
     std::optional<std::string> filename = workQ.pop();

    if (theTable.get().find(filename) == theTable.get().end()) {
      fprintf(stderr, "Mismatch between table and workQ\n");
      return -1;
    }

    // 4a&b. lookup dependencies and invoke 'process'
    process(filename.c_str(), &theTable.get()[filename]);
  }

  // 5. for each file argument
  for (i = start; i < argc; i++) {
    // 5a. create hash table in which to track file names already printed
    std::unordered_set<std::string> printed;
    // 5b. create list to track dependencies yet to print
    std::list<std::string> toProcess;

    std::pair<std::string, std::string> pair = parseFile(argv[i]);

    std::string obj = pair.first + ".o";
    // 5c. print "foo.o:" ...
    printf("%s:", obj.c_str());
    // 5c. ... insert "foo.o" into hash table and append to list
    printed.insert( obj );
    toProcess.push_back( obj );
    // 5d. invoke
    printDependencies(&printed, &toProcess, stdout);

    printf("\n");
  }

  return 0;
}
