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
for i in range(len(classinfo)):
    infolist = classinfo[i].text.split('\n')
    if len(infolist)>1:
        while ("" in infolist):
            infolist.remove("")
        if len(infolist) not in sizes:
            sizes.append(len(infolist))
        if len(infolist) >10:
            result.append({
                "name": classtitleresult[count][0],
                "regnumber": classtitleresult[count][1],
                "number": classtitleresult[count][2],
                "Registration Dates": infolist[1],
                "levels": infolist[2],
                "time": infolist[-6],
                "professor": infolist[-1],
                "days":infolist[-5],
                "where":infolist[-4],
            })
        else:
            result.append({
                "name": classtitleresult[count][0],
                "regnumber": classtitleresult[count][1],
                "number": classtitleresult[count][2],
                "Registration Dates": infolist[1],
                "levels": infolist[2],
                "time": "",
                "professor": "",
                "days": "",
                "where": "",
            })



        count=count+1

#print(sizes)
print(result)


jsonString = json.dumps(result)
jsonFile = open("CScourse.json", "w")
jsonFile.write(jsonString)
jsonFile.close()


