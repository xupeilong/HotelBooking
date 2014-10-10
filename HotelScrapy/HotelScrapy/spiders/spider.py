# coding=utf-8
import scrapy
import mysql.connector
from HotelScrapy.items import HotelInfo
from urllib import quote
import re

class DmozSpider(scrapy.Spider):
    name = "hotel"
    allowed_domains = ["hotels.ctrip.com"]
    start_urls = []
    names = []
    ids = []

    city = {
    "北京": "beijing1", 
    "天津": "tianjin3", 
    "丽江": "lijiang37", 
    "昆明": "kunming34", 
    "合肥": "qufu143", 
    "上海市": "shanghai2",
    "上海": "shanghai2",
    "中山": "zhongshan553", 
    "丽江": "lijiang37", 
    "南京": "nanjing12", 
    "南宁": "nanning380", 
    "厦门": "xiamen25",
    "台北市": "taipei617", 
    "吉林": "baishanD10215_3886", 
    "合肥": "hefei278", 
    "哈尔滨": "harbin5", 
    "大连": "dalian6", 
    "宁波": "ningbo375", 
    "安徽": "hefei278", 
    "广州": "guangzhou32", 
    "廊坊": "langfang340", 
    "惠州": "huizhou299", 
    "成都": "chengdu28", 
    "曲阜": "qufu143", 
    "杭州": "hangzhou17", 
    "桂林": "guilin33",
    "武汉": "wuhan477", 
    "洛阳": "luoyang350",
    "浙江": "hangzhou17", 
    "深圳": "shenzhen30", 
    "荔波": "libo1708",
    "西双版纳": "xishuangbanna35", 
    "西安": "xi'an10",
    "重启": "chongqing4",
    "重庆": "chongqing4", 
    "长沙": "changsha206", 
    "陕西": "xi'an10", 
    "青岛": "qingdao7", 
    "香港": "hong%20kong58", 
    }



    def __init__(self):
        conn = mysql.connector.connect(user='root', password='sdp123', database='hotel', use_unicode=True)
        cursor = conn.cursor()
        cursor.execute('select * from orderprocess_hotel')
        values = cursor.fetchall()
        # print values
        for v in values:
            cursor.execute("select * from hotel_info where hotel_id = " + str(v[0]))
            vv = cursor.fetchall()
            if (len(vv) > 0):
                continue
            base = "http://hotels.ctrip.com/hotel/"
            try:
                name = v[1].encode('utf-8')
                a1 = re.compile('\（.*\）' )
                name1 = a1.sub('', name)
                self.start_urls.append(base + self.city[v[2].encode('utf-8')] + "/k2" + quote(name1))
                self.ids.append(v[0])
            except Exception, e:
                pass
        #print self.start_urls


    def parse(self, response):
        item = HotelInfo()
        hotel = response.xpath('//div[@class="searchresult_list"][1]')
        level = hotel.xpath('//ancestor::p[@class="medal_list"]//span/@title').extract()[0].split(u'\uff08')[0]
        if len(level) > 3:
            level = level[-3:]
        area = hotel.xpath('//ancestor::p[@class="searchresult_htladdress"]//a/text()').extract()[0]
       
        url = response.url
        index = self.start_urls.index(url)
        item['hotel_id'] = self.ids[index]
        item['area'] = area
        item['level'] = level 

        url = hotel.xpath('//div[@class="hotel_pic"][1]//img/@src').extract()
        item['image_urls'] = url

        yield item
       