<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.paypal.adaptive.ipn.IPNData" %>
<%@ page import="com.paypal.adaptive.PMF" %>

<html>
  <body>
<jsp:include page="apinavigation.jsp"></jsp:include>

<%
    PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = "select from " + IPNData.class.getName();
    List<IPNData> ipnDataList = (List<IPNData>) pm.newQuery(query).execute();
    if (ipnDataList.isEmpty()) {
%>
<p>The IPNData Store has no messages.</p>
<%
    } else {
        for (IPNData g : ipnDataList) {
            if (g.getId() == null) {
%>
<p>No ID:</p>
<%
            } else {
%>
<p><b><%= g.getId() %></b> :</p>
<%
            }
%>
<blockquote><%= g.getContent() %></blockquote>
<%
        }
    }
    pm.close();
%>


  </body>
</html>