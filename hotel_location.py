# coding=utf-8
import httplib
import urllib
import mysql.connector
import json



def getLocation(address):
    url = "api.map.baidu.com:80"
    params = urllib.urlencode({'address': address, 'output': 'json', 'ak': 'O9mq1hNhAwXgSOZY0qaQXvSS'})
    conn = httplib.HTTPConnection(url)
    conn.request("GET", "/geocoder/v2/?" + params)
    responce = conn.getresponse()
    return json.loads(responce.read())

conn = mysql.connector.connect(user='root', password='sdp123', database='hotel', use_unicode=True)
cursor = conn.cursor()
cursor.execute("select * from app_hotel_info where latitude is null")
infos = cursor.fetchall()

for info in infos:
    info_id = info[0]
    hotel_id = info[1]
    cursor.execute("select * from orderprocess_hotel where id = %s", [hotel_id])
    hotel = cursor.fetchall()
    hotel_name = hotel[0][1]
    address = hotel[0][3]
    res = getLocation(hotel_name.encode('utf-8'))
    is_precise = 0
    try:
        is_precise = res['result']['precise']
    except Exception, e:
        pass
    if is_precise == 0:
        res = getLocation(address.encode('utf-8'))
    v = [int(res['result']['location']['lat'] * 1e6), int(res['result']['location']['lng'] * 1e6), info_id]
    print v
    cursor.execute("update app_hotel_info set latitude = %s , longitude = %s where id = %s", v)
    conn.commit()




