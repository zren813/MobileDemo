import requests
from bs4 import BeautifulSoup
import json
import base64
import urllib
import time
start_time = time.time()
headers={
"Host": "oscar.gatech.edu",
"Connection": "keep-alive",
"Content-Length": "351",
"Cache-Control": "max-age=0",
"sec-ch-ua": "'Chromium';v='94', 'Google Chrome';v='94', ';Not A Brand';v='99'",
"sec-ch-ua-mobile": "?0",
"sec-ch-ua-platform": "Windows",
"Upgrade-Insecure-Requests": "1",
"Origin": "https://oscar.gatech.edu",
"Content-Type": "application/x-www-form-urlencoded",
"User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36",
"Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
"Sec-Fetch-Site": "same-origin",
"Sec-Fetch-Mode": "navigate",
"Sec-Fetch-User": "?1",
"Sec-Fetch-Dest": "document",
"Referer": "https://oscar.gatech.edu/bprod/bwckschd.p_get_crse_unsec",
"Accept-Encoding": "gzip, deflate, br",
"Accept-Language": "zh-CN,zh;q=0.9",
"Cookie": "s_pers=%20v8%3D1631833903752%7C1726441903752%3B%20v8_s%3DFirst%2520Visit%7C1631835703752%3B%20c19%3Dsd%253Asearch%253Aresults%253Acustomer-standard%7C1631835703757%3B%20v68%3D1631833903722%7C1631835703771%3B; AMCV_4D6368F454EC41940A4C98A6%40AdobeOrg=-1124106680%7CMCIDTS%7C18887%7CMCMID%7C64560860216285563521752091607413453141%7CMCAAMLH-1632438703%7C7%7CMCAAMB-1632438703%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1631841103s%7CNONE%7CMCAID%7CNONE%7CMCCIDH%7C910767293%7CvVersion%7C5.2.0; utag_main=v_id:017bffbad00a006250590202c9e00306e005d06600994$_sn:1$_se:1$_ss:1$_st:1632084838219$ses_id:1632083038219%3Bexp-session$_pn:1%3Bexp-session$vapi_domain:gatech.edu; AMCV_8E929CC25A1FB2B30A495C97%40AdobeOrg=1687686476%7CMCIDTS%7C18890%7CMCMID%7C64320745708588873071768612492680330654%7CMCAAMLH-1632687838%7C7%7CMCAAMB-1632687838%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1632090238s%7CNONE%7CMCAID%7CNONE%7CvVersion%7C3.0.0; _ga=GA1.3.804958847.1629925955; _ga_VDQQS8T7FC=GS1.1.1636426860.1.1.1636428629.0; _ga=GA1.2.804958847.1629925955; BIGipServer~BANNER~oscar.coda_443=555452938.64288.0000; _gid=GA1.2.952817317.1638052087; _gat_gtag_UA_142556041_1=1; _gid=GA1.3.952817317.1638052087; _gat_UA-142556041-1=1",
}

submit = {
    "term_in": "202202".encode("utf-8"),
    "sel_subj": "dummy".encode("utf-8"),
    "sel_day": "dummy".encode("utf-8"),
    "sel_schd": "dummy".encode("utf-8"),
    "sel_insm": "dummy".encode("utf-8"),
    "sel_camp": "dummy".encode("utf-8"),
    "sel_levl": "dummy".encode("utf-8"),
    "sel_sess": "dummy".encode("utf-8"),
    "sel_instr": "dummy".encode("utf-8"),
    "sel_ptrm": "dummy".encode("utf-8"),
    "sel_attr": "dummy".encode("utf-8"),
    "sel_subj": "CS".encode("utf-8"),
    "sel_crse": "".encode("utf-8"),
    "sel_title": "".encode("utf-8"),
    "sel_schd":"%".encode("utf-8"),
    "sel_from_cred":"".encode("utf-8"),
    "sel_to_cred":"".encode("utf-8"),
    "sel_camp": "%".encode("utf-8"),
    "sel_ptrm": "%".encode("utf-8"),
    "sel_instr": "%".encode("utf-8"),
    "sel_attr": "%".encode("utf-8"),
    "begin_hh": "0".encode("utf-8"),
    "begin_mi": "0".encode("utf-8"),
    "begin_ap": "a".encode("utf-8"),
    "end_hh": "0".encode("utf-8"),
    "end_mi": "0".encode("utf-8"),
    "end_ap": "a".encode("utf-8"),


}

data = requests.post(url='https://oscar.gatech.edu/bprod/bwckschd.p_get_crse_unsec',data=submit,headers=headers)
print(data.text)





