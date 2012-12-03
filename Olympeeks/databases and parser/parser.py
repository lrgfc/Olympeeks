from xml.dom.minidom import parse, parseString
from storm.locals import *

# test case

x = """<Medals>
<medals year="1896">
<record>
<rk>1 </rk>
<country>Greece</country>
<gold>10 </gold>
<silver>18 </silver>
<bronze>19 </bronze>
<total>47 </total>
</record>
<record>
<rk>2 </rk>
<country>United States</country>
<gold>11 </gold>
<silver>7 </silver>
<bronze>2 </bronze>
<total>20 </total>
</record>
<record>
<rk>3 </rk>
<country>Germany</country>
<gold>6 </gold>
<silver>5 </silver>
<bronze>2 </bronze>
<total>13 </total>
</record>
<record>
<rk>4 </rk>
<country>France</country>
<gold>5 </gold>
<silver>4 </silver>
<bronze>2 </bronze>
<total>11 </total>
</record>
<record>
<rk>5 </rk>
<country>Great Britain</country>
<gold>2 </gold>
<silver>3 </silver>
<bronze>2 </bronze>
<total>7 </total>
</record>
<record>
<rk>6 </rk>
<country>Hungary</country>
<gold>2 </gold>
<silver>1 </silver>
<bronze>3 </bronze>
<total>6 </total>
</record>
<record>
<rk>7 </rk>
<country>Denmark</country>
<gold>1 </gold>
<silver>2 </silver>
<bronze>3 </bronze>
<total>6 </total>
</record>
<record>
<rk>8 </rk>
<country>Austria</country>
<gold>2 </gold>
<silver>1 </silver>
<bronze>2 </bronze>
<total>5 </total>
</record>
<record>
<rk>9 </rk>
<country>Switzerland</country>
<gold>1 </gold>
<silver>2 </silver>
<bronze>0 </bronze>
<total>3 </total>
</record>
<record>
<rk>10 </rk>
<country>Australia</country>
<gold>2 </gold>
<silver>0 </silver>
<bronze>0 </bronze>
<total>2 </total>
</record>
<record>
<rk>11 </rk>
<country>Mixed team</country>
<gold>1 </gold>
<silver>0 </silver>
<bronze>1 </bronze>
<total>2 </total>
</record>
</medals>
<medals year="1900">
<record>
<rk>1 </rk>
<country>France</country>
<gold>27 </gold>
<silver>39 </silver>
<bronze>37 </bronze>
<total>103 </total>
</record>
<record>
<rk>2 </rk>
<country>United States</country>
<gold>19 </gold>
<silver>14 </silver>
<bronze>14 </bronze>
<total>47 </total>
</record>
<record>
<rk>3 </rk>
<country>Great Britain</country>
<gold>15 </gold>
<silver>8 </silver>
<bronze>9 </bronze>
<total>32 </total>
</record>
<record>
<rk>4 </rk>
<country>Mixed team</country>
<gold>7 </gold>
<silver>4 </silver>
<bronze>7 </bronze>
<total>18 </total>
</record>
<record>
<rk>5 </rk>
<country>Belgium</country>
<gold>6 </gold>
<silver>7 </silver>
<bronze>4 </bronze>
<total>17 </total>
</record>
<record>
<rk>6 </rk>
<country>Switzerland</country>
<gold>6 </gold>
<silver>2 </silver>
<bronze>1 </bronze>
<total>9 </total>
</record>
<record>
<rk>7 </rk>
<country>Germany</country>
<gold>4 </gold>
<silver>3 </silver>
<bronze>2 </bronze>
<total>9 </total>
</record>
<record>
<rk>8 </rk>
<country>Denmark</country>
<gold>1 </gold>
<silver>3 </silver>
<bronze>2 </bronze>
<total>6 </total>
</record>
<record>
<rk>9 </rk>
<country>Netherlands</country>
<gold>1 </gold>
<silver>2 </silver>
<bronze>3 </bronze>
<total>6 </total>
</record>
<record>
<rk>10 </rk>
<country>Austria</country>
<gold>0 </gold>
<silver>3 </silver>
<bronze>3 </bronze>
<total>6 </total>
</record>
<record>
<rk>11 </rk>
<country>Italy</country>
<gold>3 </gold>
<silver>2 </silver>
<bronze>0 </bronze>
<total>5 </total>
</record>
<record>
<rk>12 </rk>
<country>Australia</country>
<gold>2 </gold>
<silver>0 </silver>
<bronze>3 </bronze>
<total>5 </total>
</record>
<record>
<rk>13 </rk>
<country>Hungary</country>
<gold>1 </gold>
<silver>2 </silver>
<bronze>2 </bronze>
<total>5 </total>
</record>
<record>
<rk>14 </rk>
<country>Norway</country>
<gold>0 </gold>
<silver>2 </silver>
<bronze>3 </bronze>
<total>5 </total>
</record>
<record>
<rk>15 </rk>
<country>Cuba</country>
<gold>1 </gold>
<silver>1 </silver>
<bronze>0 </bronze>
<total>2 </total>
</record>
<record>
<rk>16 </rk>
<country>Canada</country>
<gold>1 </gold>
<silver>0 </silver>
<bronze>1 </bronze>
<total>2 </total>
</record>
<record>
<rk>17 </rk>
<country>India</country>
<gold>0 </gold>
<silver>2 </silver>
<bronze>0 </bronze>
<total>2 </total>
</record>
<record>
<rk>18 </rk>
<country>Bohemia</country>
<gold>0 </gold>
<silver>1 </silver>
<bronze>1 </bronze>
<total>2 </total>
</record>
<record>
<rk>19 </rk>
<country>Luxembourg</country>
<gold>1 </gold>
<silver>0 </silver>
<bronze>0 </bronze>
<total>1 </total>
</record>
<record>
<rk>20 </rk>
<country>Spain</country>
<gold>1 </gold>
<silver>0 </silver>
<bronze>0 </bronze>
<total>1 </total>
</record>
<record>
<rk>21 </rk>
<country>Sweden</country>
<gold>0 </gold>
<silver>0 </silver>
<bronze>1 </bronze>
<total>1 </total>
</record>
</medals>
</Medals>"""

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

database = create_database("sqlite:/Users/lrgfc/test2.sqlite")
store = Store(database)

store.execute("CREATE TABLE test "
              "(year integer, rank integer, country varchar, gold integer, silver integer,bronze integer, total integer)")

dom1 = parseString(x)
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





