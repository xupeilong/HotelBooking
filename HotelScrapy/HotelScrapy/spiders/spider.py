# coding=utf-8
import scrapy
import mysql.connector
from HotelScrapy.items import HotelInfo
from HotelScrapy.items import HouseInfo
from urllib import quote
import re
import json
from scrapy.selector import Selector

class DmozSpider(scrapy.Spider):
    name = "hotel"
    allowed_domains = ["hotels.ctrip.com"]
    start_urls = []

    city = {
    "北京": "beijing1", 
    "天津": "tianjin3", 
    "丽江": "lijiang37", 
    "昆明": "kunming34", 
    "合肥": "qufu143", 
    "上海": "shanghai2",
    "中山": "zhongshan553", 
    "丽江": "lijiang37", 
    "南京": "nanjing12", 
    "南宁": "nanning380", 
    "厦门": "xiamen25",
    "台北": "taipei617", 
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
    "雅安": "ya'an3277",
    "沈阳": "shenyang451",
    "三亚": "sanya43",
    "郑州": "zhengzhou559",
    "潍坊": "weifang475",
    "齐齐哈尔": "qiqihar149",
    "白山": "baishan3886",
    "长春": "changchun158"
    }

    to_do_hotel_list = []



    def start_requests(self):
        conn = mysql.connector.connect(user='root', password='sdp123', database='hotel', use_unicode=True)
        cursor = conn.cursor()
        cursor.execute('select * from OrderProcess_hotel')
        values = cursor.fetchall()
        # values = cursor.fetchmany(1)
        # cursor.fetchall()
        for v in values:
            #check repeat
            cursor.execute("select * from app_hotel_info where hotel_id = " + str(v[0]))
            vv = cursor.fetchall()
            if (len(vv) > 0):
                continue
            base = "http://hotels.ctrip.com/hotel/"
            name = v[1].encode('gbk')
            a1 = re.compile('\xa3\xa8.*\xa3\xa9')  
            name0 = a1.sub('', name)
            name1 = name0.replace('\xc9\xcc\xce\xf1', '')
            name1 = name1.replace('\t', '')
            name1 = name1.replace('\n', '')
            # print name1
            req = ''
            
            city = v[2]
            city = city.replace(' ', '')
            city = city.replace('\xca\xd0', '')
            if len(city) > 10:
                continue
            if city.encode('utf-8') == '素叻他尼':
                continue
            if city.encode('utf-8') == '曼谷':
                continue
            if city.encode('utf-8') == '长白山':
                continue
            name1 = name1.replace(city.encode('gbk'), '')
            name1 = name1.replace('\xbe\xc6\xb5\xea', '')
            name1 = name1.replace('\xbf\xec\xbd\xdd', '')
            print quote(name1)
            print city
            path = (v[0], base + self.city[city.replace(' ', '').encode('utf-8')] + "/k2" + quote(name1))
            self.to_do_hotel_list.append(path)

        for path in self.to_do_hotel_list:
            try:
                req = scrapy.Request(path[1])
                req.meta['hotel_id'] = path[0]   
                yield req
                print "******* Hotels to do: "
                print len(self.to_do_hotel_list)
            except Exception, e:
                raise e
                pass
        
            

            

    def parse(self, response):
        hotel_id = response.meta['hotel_id']
        is_not_found = response.xpath("/html/body[@id='mainbody']/form[@id='aspnetForm']/div[@id='base_bd']/div[@id='J_mainBox']"
            + "/div[@class='base_main3']/div[@id='J_noticeHtml']/div[@id='no_tips']/div[@id='divNoresult']/strong").extract()
        if len(is_not_found):
            url = response.url
            if url.find('k2') >= 0:
                url = url.replace('k2', 'k1')
                req = scrapy.Request(url)
                req.meta['hotel_id'] = hotel_id
                yield req
            else:
                print '******************** Not found: id = ' + str(hotel_id)
            return
        hotel_page_id = response.xpath('/html/body/form/div/div/div/div[@id="hotel_list"]/div[1]/@id').extract()[0]
        # print hotel_page_id
        try:
            pass
        except Exception, e:
            raise e
        hotel_image_url = response.xpath("/html/body[@id='mainbody']/form[@id='aspnetForm']/div[@id='base_bd']/"
         + "div[@id='J_mainBox']/div[@class='base_main3']/div[@id='hotel_list']/div[1]/"
         + "ul[@class='searchresult_info']/li[@class='pic_medal']/div[@class='hotel_pic']/a/"
         + "img/@src").extract()[0]
        # print "***" + str(len(hotel_image_url))

        hotel_base_url = 'http://hotels.ctrip.com/hotel/'
        hotel_url = hotel_base_url + str(hotel_page_id) + '.html'

        req = scrapy.Request(hotel_url, callback=self.parse_hotel)
        req.meta['hotel_id'] = hotel_id
        req.meta['hotel_page_id'] = hotel_page_id
        req.meta['hotel_image_url'] = hotel_image_url
        yield req

    def parse_hotel(self, response):
        
        hotel_id = response.meta['hotel_id']
        hotel_page_id = response.meta['hotel_page_id']
        hotel_image_url = response.meta['hotel_image_url']
        print '***********************************'
        print hotel_id
        item = HotelInfo()
        level = ""
        try:
            level = response.xpath("/html/body[@id='mainbody']/form[@id='aspnetForm']/div[@id='base_bd']"
            + "/div[@class='htl_info_com layoutfix']/div[@class='htl_info']/div[@class='grade']/"
            + "span/@title").extract()[0].split(u'\uff08')[0]
        except Exception, e:
            level = "0"
        
        if len(level) > 3:
            level = level[-3:]
        
        area_result = response.xpath("/html/body[@id='mainbody']/form[@id='aspnetForm']/div[@id='base_bd']/"
         + "div[@class='htl_info_com layoutfix']/div[@class='htl_info']/div[@class='adress']/"
         + "a/text()").extract()
        area = 'none'
        if len(area_result) > 0:
            area = area_result[0]

        # hotel = response.xpath('//div[@class="searchresult_list"][1]')
        # level = hotel.xpath('//ancestor::p[@class="medal_list"]//span/@title').extract()[0].split(u'\uff08')[0]
        # if len(level) > 3:
        #     level = level[-3:]
        # area = hotel.xpath('//ancestor::p[@class="searchresult_htladdress"]//a/text()').extract()[0]
        # url = hotel.xpath('//div[@class="hotel_pic"][1]//img/@src').extract()
        item['hotel_id'] = hotel_id
        item['area'] = area
        item['level'] = level 
        item['image_url'] = hotel_image_url
        yield item

        house_list_url = 'http://hotels.ctrip.com/Domestic/tool/AjaxHotelRoomListForDetail.aspx?hotel=' + str(hotel_page_id)
        req = scrapy.Request(house_list_url, callback=self.parse_house)
        req.meta['hotel_id'] = hotel_id
        # yield req

    def parse_house(self, response):
        hotel_id = response.meta['hotel_id']        
        obj = json.loads(response.body, "gbk")
        html = obj['html']
        rooms = Selector(text=html).xpath('//td[@class="room_type"]')
        for room in rooms:
            pic_url_list = room.xpath('.//img/@src').extract()
            if len(pic_url_list) > 0:
                pic_url = pic_url_list[0]
            else:
                pic_url = 'none'   
            room_name = room.xpath('.//a[@class="room_unfold"]/text()').extract()[0]
            room_name = room_name.replace('\r', '')
            room_name = room_name.replace('\n', '')
            item = HouseInfo()
            item['image_url'] = pic_url
            item['house_name'] = room_name
            item['hotel_id'] = hotel_id
            yield item
        




       