# author: chenxi chauncey wang
# this program will create a table for summer olmpic game.
# the modified xml file is at /Users/lrgfc/Downloads/cis550 project/

from xml.dom.minidom import parse, parseString
from storm.locals import *

class Test(object):
    __storm_table__ = "test"
    year = Int(primary = True)
    rank = Int()
    country = Unicode()
    gold = Int()
    silver = Int()
    bronze = Int()
    total = Int()


def getText(nodelist):
    rc = []
    for node in nodelist:
        if node.nodeType == node.TEXT_NODE:
            rc.append(node.data)
    return ''.join(rc)

database = create_database("sqlite:/Users/lrgfc/WinterOlympics.sqlite")
store = Store(database)

store.execute("CREATE TABLE test "
              "(year integer, rank integer, country varchar, gold integer, silver integer,bronze integer, total integer, primary key(year, rank))")


x ='/Users/lrgfc/Downloads/cis550 project/WinterOlympicsChauncey.xml'
dom1 = parse(x)
medals = dom1.getElementsByTagName('medals')

for medal in medals:
    print medal.attributes['year'].value
    rks = medal.getElementsByTagName('record')
    for rk in rks:
        x = Test()
        x.year = int(medal.attributes['year'].value)

        ranks = rk.getElementsByTagName('rk')
        x.rank = int(getText(ranks[0].childNodes))

        countries = rk.getElementsByTagName('country')
        x.country = getText(countries[0].childNodes)

        golds = rk.getElementsByTagName('gold')
        x.gold = int(getText(golds[0].childNodes))

        silvers = rk.getElementsByTagName('silver')
        x.silver = int(getText(silvers[0].childNodes))

        bronzes = rk.getElementsByTagName('bronze')
        x.bronze = int(getText(bronzes[0].childNodes))

        totals = rk.getElementsByTagName('total')
        x.total = int(getText(totals[0].childNodes))

        store.add(x)

        print "%r, %r, %r, %r, %r, %r, %r" % (x.year, x.rank, x.country, x.gold, x.silver, x.bronze, x.total)

store.commit()