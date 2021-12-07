from bs4 import BeautifulSoup
import json

path = './Class Schedule Listing.html'
htmlfile = open(path, 'r', encoding='utf-8')

htmlhandle = htmlfile.read()

soup = BeautifulSoup(htmlhandle,'lxml')

classtitle = soup.find_all(attrs={'class': 'ddtitle'})
classinfo = soup.find_all(attrs={'class': 'dddefault'})

classtitleresult=[]
classinforesult = []

courselist = []

result = []
for i in range(len(classtitle)):
    classtitleresult.append(classtitle[i].text.split('-'))


count = 0
sizes = []

numberlist = []
for i in range(len(classinfo)):
    infolist = classinfo[i].text.split('\n')

    if len(infolist)>1:
        while ("" in infolist):
            infolist.remove("")
        if len(infolist) not in sizes:
            sizes.append(len(infolist))
        coursename = ""
        num = 3
        while len(classtitleresult[count]) > num:
            coursename = classtitleresult[count][len(classtitleresult[count]) - num - 1] + ' ' + coursename
            num = num + 1

        dict={}
        credit = ""
        otherinfo = []
        campus = ""
        for info in infolist:
            if info.count(":")==1:
                halfinfo = info.split(':')
                dict[halfinfo[0]]=halfinfo[1]
            elif "Credits" in info:
                credit = info

            elif "Campus" in info and "only" not in info:
                campus=info



            elif info == 'View Catalog Entry':
                break

            elif info != 'Syllabus Available' and info !='Lecture* Schedule Type':
                otherinfo.append(info)




        if len(infolist) >10:
            dict["name"] = coursename
            dict["regnumber"] = classtitleresult[count][-3]
            dict["number"] = classtitleresult[count][-2]
            numb = classtitleresult[count][-2]
            if (numb not in numberlist):
                numberlist.append(numb)
            dict["time"] = infolist[-6]
            dict["days"] = infolist[-5]
            dict["where"] = infolist[-4]
            dict["Date Range"] = infolist[-3]
            dict["Schedule Type"] = infolist[-2]
            dict["professor"] = infolist[-1]
            dict["campus"] = campus
            dict["credit"] = credit
            dict["otherinfo"] = otherinfo

            result.append(dict)
        else:
            dict["name"] = coursename
            dict["regnumber"] = classtitleresult[count][-3]
            dict["number"] = classtitleresult[count][-2]
            numb = classtitleresult[count][-2]
            if (numb not in numberlist):
                numberlist.append(numb)
            dict["time"] = ""
            dict["days"] = ""
            dict["where"] = ""
            dict["Date Range"] = ""
            dict["Schedule Type"] = ""
            dict["professor"] = ""
            dict["campus"] = campus
            dict["credit"] = credit
            dict["otherinfo"] = otherinfo

            result.append(dict)

        count=count+1



jsonString = json.dumps(result)
jsonFile = open("CScourse.json", "w")
jsonFile.write(jsonString)
jsonFile.close()
print(numberlist)


numbersTXT = open("CSnumbers.txt", "w")
numbersTXT.write(json.dumps(numberlist))
numbersTXT.close()




