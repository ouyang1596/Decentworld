CREATE TABLE recommendBenefitDetail (Id INTEGER PRIMARY KEY AUTOINCREMENT, status INTEGER, time TEXT, amount REAL, userID TEXT);
CREATE TABLE recommendBenefitList (Id INTEGER PRIMARY KEY AUTOINCREMENT, benefit REAL, name TEXT, amount REAL, isFriend INTEGER, userID TEXT, otherID TEXT, isRegister INTEGER);
