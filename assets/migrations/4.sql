DROP TABLE CommentEntity;
DROP TABLE MomentEntity;
DROP TABLE dWMMessage;
DROP TABLE dWSMessage;
DROP TABLE praise;
DROP TABLE trample;
DROP TABLE loginUser;
CREATE TABLE momentEntity (userID TEXT, content TEXT, level INTEGER, localUrl TEXT, publisherName TEXT, momentState INTEGER, money REAL, remoteID INTEGER, remoteUrl TEXT, Id INTEGER PRIMARY KEY AUTOINCREMENT, publisherID TEXT, blocklist TEXT, downCount INTEGER, likeCount INTEGER, dislikeCount INTEGER, publishTime INTEGER, onlyshowtolist TEXT, contentType INTEGER, commentCount INTEGER);
CREATE TABLE commentEntity (momentId INTEGER, Id INTEGER PRIMARY KEY AUTOINCREMENT, time INTEGER, dwID TEXT, userID TEXT, publisherID TEXT, content TEXT, type INTEGER, commentState INTEGER, reply TEXT, commentID INTEGER);