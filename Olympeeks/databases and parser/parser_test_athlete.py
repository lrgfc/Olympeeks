#!/usr/bin/python
# -*- coding: utf8 -*-

# author: chenxi chauncey wang
# this program will create a table for athlete table.
# the modified xml file is at /Users/lrgfc/Downloads/cis550 project/
import os, sys

from xml.dom.minidom import parse, parseString
from storm.locals import *
# template 
# <Record>
# <Full_Name> Christine Jacoba Aaftink </Full_Name>
# <Gender>Female </Gender>
# <DOB>August 25, 1966 in Abcoude, Utrecht, Netherlands </DOB>
# <Country>Netherlands </Country>
# <Sport>Speed Skating</Sport>
# <Medal_history><Games>1988 Winter</Games><Age>21</Age><City>Calgary</City><Sport>Speed Skating</Sport><Event>Women's 500 metres</Event><Team>Netherlands</Team><NOC>NED</NOC><Rank>17</Rank><Medal></Medal></Medal_history>
# <Medal_history><Games>1988 Winter</Games><Age>21</Age><City>Calgary</City><Sport>Speed Skating</Sport><Event>Women's 1,000 metres</Event><Team>Netherlands</Team><NOC>NED</NOC><Rank>12</Rank><Medal></Medal></Medal_history>
# <Medal_history><Games>1992 Wiutf8nter</Games><Age>25</Age><City>Albertville</City><Sport>Speed Skating</Sport><Event>Women's 500 metres</Event><Team>Netherlands</Team><NOC>NED</NOC><Rank>5</Rank><Medal></Medal></Medal_history>
# <Medal_history><Games>1992 Winter</Games><Age>25</Age><City>Albertville</City><Sport>Speed Skating</Sport><Event>Women's 1,000 metres</Event><Team>Netherlands</Team><NOC>NED</NOC><Rank>4</Rank><Medal></Medal></Medal_history>
# <Medal_history><Games>1994 Winter</Games><Age>27</Age><City>Lillehammer</City><Sport>Speed Skating</Sport><Event>Women's 500 metres</Event><Team>Netherlands</Team><NOC>NED</NOC><Rank>19</Rank><Medal></Medal></Medal_history>
# <Medal_history><Games>1994 Winter</Games><Age>27</Age><City>Lillehammer</City><Sport>Speed Skating</Sport><Event>Women's 1,000 metres</Event><Team>Netherlands</Team><NOC>NED</NOC><Rank>20</Rank><Medal></Medal></Medal_history>
# </Record>

x = """<Athlete><Record><Full_Name> Gunnar Nielsen Aaby </Full_Name><Gender>Male </Gender><DOB>July 9, 1895 in Frederiksberg, Hovedstaden, Denmark </DOB><Country>Denmark </Country><Sport>Football</Sport><Medal_history><Games>1920 Summer</Games><Age>24</Age><City>Antwerpen</City><Sport>Football</Sport><Event>Men's Football</Event><Team>Denmark</Team><NOC>DEN</NOC><Rank>8T</Rank><Medal></Medal></Medal_history></Record><Record><Full_Name> Edgar Lindenau Aabye </Full_Name><Gender>Male </Gender><DOB>September 1, 1865 in Helsingør, Hovedstaden, Denmark </DOB><Country>Denmark </Country><Sport>Tug-Of-War Medals: 1 Gold (1 Total)</Sport><Medal_history><Games>1900 Summer</Games><Age>34</Age><City>Paris</City><Sport>Tug-Of-War</Sport><Event>Men's Tug-Of-War</Event><Team>Denmark/Sweden</Team><NOC>DEN</NOC><Rank>1</Rank><Medal>Gold</Medal></Medal_history></Record><Record>
<Full_Name> Christine Jacoba Aaftink </Full_Name>
<Gender>Female </Gender>
<DOB>August 25, 1966 in Abcoude, Utrecht, Netherlands </DOB>
<Country>Netherlands </Country>
<Sport>Speed Skating</Sport>
<Medal_history><Games>1988 Winter</Games><Age>21</Age><City>Calgary</City><Sport>Speed Skating</Sport><Event>Women's 500 metres</Event><Team>Netherlands</Team><NOC>NED</NOC><Rank>17</Rank><Medal></Medal></Medal_history>
<Medal_history><Games>1988 Winter</Games><Age>21</Age><City>Calgary</City><Sport>Speed Skating</Sport><Event>Women's 1,000 metres</Event><Team>Netherlands</Team><NOC>NED</NOC><Rank>12</Rank><Medal></Medal></Medal_history>
<Medal_history><Games>1992 Winter</Games><Age>25</Age><City>Albertville</City><Sport>Speed Skating</Sport><Event>Women's 500 metres</Event><Team>Netherlands</Team><NOC>NED</NOC><Rank>5</Rank><Medal></Medal></Medal_history>
<Medal_history><Games>1992 Winter</Games><Age>25</Age><City>Albertville</City><Sport>Speed Skating</Sport><Event>Women's 1,000 metres</Event><Team>Netherlands</Team><NOC>NED</NOC><Rank>4</Rank><Medal></Medal></Medal_history>
<Medal_history><Games>1994 Winter</Games><Age>27</Age><City>Lillehammer</City><Sport>Speed Skating</Sport><Event>Women's 500 metres</Event><Team>Netherlands</Team><NOC>NED</NOC><Rank>19</Rank><Medal></Medal></Medal_history>
<Medal_history><Games>1994 Winter</Games><Age>27</Age><City>Lillehammer</City><Sport>Speed Skating</Sport><Event>Women's 1,000 metres</Event><Team>Netherlands</Team><NOC>NED</NOC><Rank>20</Rank><Medal></Medal></Medal_history></Record></Athlete>"""

y = '/Users/lrgfc/Downloads/cis550 project/Athletes.xml'

class test(object):
    __storm_table__ = "athelets"
    full_Name = Unicode(primary = True)
    gender = Unicode() 
    country = Unicode()
    sport = Unicode()
    event = Unicode()
    game = Unicode()
    team = Unicode()
    medal = Unicode()
    rank = Unicode()


def getText(nodelist):
    rc = []
    for node in nodelist:
        if node.nodeType == node.TEXT_NODE:
            rc.append(node.data)
    return ''.join(rc)
database = create_database("sqlite:/Users/lrgfc/Athelets.sqlite")
store = Store(database)

store.execute("CREATE TABLE athelets "
              "(full_Name varchar, gender varchar, country varchar, sport varchar,event varchar, game varchar,team varchar, medal varchar,rank varchar)")

# dom1 = parseString(x)
dom1 = parse(y)
records = dom1.getElementsByTagName('Record')

for record in records:
    m_historys = record.getElementsByTagName('Medal_history')
    full_Names = record.getElementsByTagName('Full_Name')
    genders = record.getElementsByTagName('Gender')
    countries = record.getElementsByTagName('Country')
    for m_history in m_historys:
        x = test()
        x.full_Name = getText(full_Names[0].childNodes)
        x.gender = getText(genders[0].childNodes)
        x.country = getText(countries[0].childNodes)
        
        games = m_history.getElementsByTagName('Games')
        sports = m_history.getElementsByTagName('Sport')
        events = m_history.getElementsByTagName('Event')
        teams = m_history.getElementsByTagName('Team')
        medals = m_history.getElementsByTagName('Medal')
        ranks = m_history.getElementsByTagName('Rank')

        x.sport = unicode(getText(sports[0].childNodes))
        x.game = unicode(getText(games[0].childNodes))
        x.event = unicode(getText(events[0].childNodes))
        x.team = unicode(getText(teams[0].childNodes))
        x.medal = unicode(getText(medals[0].childNodes))
        x.rank = unicode(getText(ranks[0].childNodes))

        # print "%r, %r, %r, %r, %r, %r, %r, %r, %r" % (x.full_Name, x.gender, x.country, x.game, x.sport, x.event, x.team, x.medal, x.rank)

        store.add(x)

store.commit()





