<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="com.google.gdata.data.photos.PhotoEntry" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
    "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" > 
<head> 
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" /> 
<title>PicMart - your online store for buys photo prints!</title>

<style type="text/css"> 
/* choose a suitable font and center the #container div in Internet Explorer */
body {text-align:center; font-family: tahoma; arial, sans-serif; font-size:76%; letter-spacing:0.05em;}
 
/* The containing box for the gallery. */
#container {position:relative; width:770px; height:396px; margin:20px auto 0 auto; border:1px solid #aaa; background:#fff url(../images/back.jpg) 75px 10px no-repeat;}
 
/* Removing the list bullets and indentation - add size - and position */
#container ul {width:198px; height:386px; padding:0;  margin:5px; list-style-type:none; float:right;}
 
#container li {float:left;}
 
/* Remove the images and text from sight */
#container a.gallery span {position:absolute; width:1px; height:1px; top:5px; left:5px; overflow:hidden; background:#fff;}
 
/* Adding the thumbnail images */
#container a.gallery, #container a.gallery:visited {display:block; color:#000; text-decoration:none; border:1px solid #000; margin:1px 2px 1px 2px; text-align:left; cursor:default;}
<%
	List<PhotoEntry> photos = (List) request.getAttribute("photos");
	
	for(int i = 0 ; i < photos.size(); i++){
		PhotoEntry photo = photos.get(i);
		out.println("#container a.slide" + i + " {background:url("
				+ photo.getMediaThumbnails().get(0).getUrl()
				+ "); height:93px; width:60px;}");
	}
%>
 /* styling the hovers */
#container a.gallery:hover {border:1px solid #fff;}
#container a.gallery:hover span {position:absolute; width:372px; height:372px; top:10px; left:75px; color:#000; background:#fff;}
#container a.gallery:hover img {border:1px solid #fff; float:left; margin-right:5px;}
#container a.slideb:hover img, #container a.slidei:hover img {float:right;}
 
</style> 
</head>
<body>

<div id="container"> 
<ul> 
<%

	for(int i = 0 ; i < photos.size(); i++){
		PhotoEntry photo = photos.get(i);
		out.print("<li><a class=\"gallery slide" + i + "\" href=\"/picmart?id="+ photo.getMediaThumbnails().get(2).getUrl()
					+ "&title=" + photo.getTitle().getPlainText()
				 	+ "\"><span><img src=\"" 
					+ photo.getMediaThumbnails().get(2).getUrl()
					+ "\" alt=\""+ photo.getTitle().getPlainText() + "\" title=\""+ photo.getTitle().getPlainText() 
					+"\" /><br />"+ photo.getTitle().getPlainText() +"<br /></span></a></li>");

	}
%>
</ul> 
</div> 



</body>
</html>