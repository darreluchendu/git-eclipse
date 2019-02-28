from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.common.keys import Keys

browser=webdriver.Chrome('C:/Users/Darrel/Downloads/chromedriver_win32/chromedriver')
browser.get("https://discordapp.com/channels/417064769309245471/417098868337934346")
inputt =browser.find_elements_by_tag_name('input')
inputt[0].send_keys("stfiniansdarreluchendu@gmail.com")
inputt[1].send_keys("driving10")

delay = 10 # seconds
button =browser.find_element_by_tag_name('button')
button.submit()

textarea =WebDriverWait(browser, delay).until(EC.presence_of_element_located((By.TAG_NAME, 'textarea')))
textarea.send_keys("!daily")
textarea.send_keys(Keys.ENTER)
textarea.send_keys(">daily")
textarea.send_keys(Keys.ENTER)

import sys
sys.exit()

 