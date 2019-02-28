#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <netdb.h>




int main(int argc, char *argv[])
{
    char * output_fam;
    struct addrinfo hints, *ai, *ai0;
    char dst[INET6_ADDRSTRLEN];


    memset(&hints, 0, sizeof(hints));
    hints.ai_family =  PF_UNSPEC;    // Unspecified protocol (IPv4 or IPv6 okay)
    hints.ai_socktype = SOCK_STREAM; 

    for( char ** i =argv+1; *i != argv[argc]; i++)
{
    getaddrinfo(*i, "5000", &hints, &ai0);
    for (ai = ai0; ai != NULL; ai = ai->ai_next){
        if (ai->ai_family==AF_INET){
            struct sockaddr_in * addr4=(struct sockaddr_in *)  ai->ai_addr;
            output_fam="IPv4";
            if (inet_ntop(AF_INET, &(addr4->sin_addr), dst,  INET_ADDRSTRLEN)==NULL){
             perror("inet_ntop");
               exit(EXIT_FAILURE);
           }
        }else{
            struct sockaddr_in6 * addr6=(struct sockaddr_in6 *)  ai->ai_addr;
           output_fam="IPv6";
           if( inet_ntop(AF_INET6, &(addr6->sin6_addr), dst,  INET6_ADDRSTRLEN)==NULL){
                perror("inet_ntop");
               exit(EXIT_FAILURE);
           }
        }
        printf("www.%s %s %s\n",*i , output_fam,dst);

    }
}

    return 0;
}
