<%--
 * index.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('ADMIN_BAN') or hasRole('HANDY_WORKER_BAN') or hasRole('CUSTOMER_BAN') or hasRole('SPONSOR_BAN') or hasRole('REFEREE_BAN')">

<img src="https://media1.tenor.com/images/5c5fa62b62abf010527c3f770d730ce7/tenor.gif?itemid=11795933" />
<br/>
<b><spring:message code="profile.ban.message" /> </b>

</security:authorize>
