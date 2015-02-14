# coding=utf-8
# -*- coding: utf-8 -*-
from selenium import webdriver
import sys
reload(sys)
sys.setdefaultencoding('utf-8')
print 'start'
driver = webdriver.PhantomJS()
driver.get('http://www.baidu.com/')
data = driver.page_source

print data.encode('gbk').replace(u'\xbb', ' ')
print 'end'
# print data.decode('utf-8')
