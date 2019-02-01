<%--
 * action-2.jsp
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

<security:authorize access="hasRole('CUSTOMER')">
	
<jstl:choose>
    <jstl:when test="${not empty error}">
    
        <p style="color:red"> <spring:message code="quolet.error" /> </p>
        
    </jstl:when>    
    <jstl:otherwise>
    
        <p><spring:message code="quolet.list" /></p>
		<display:table pagesize="5" name="quolets" id="row" requestURI="${requestURI}" >
		<display:column property="ticker" titleKey="quolet.ticker" />
		<display:column property="moment" titleKey="quolet.moment" />
		<display:column property="body" titleKey="quolet.body" />
		<display:column property="picture" titleKey="quolet.picture" />	
		
		<display:column titleKey="fixUpTask.show">
			<a href="quolet/customer/show.do?quoletId=${row.id}">
				<spring:message code="fixUpTask.show" /></a>
		</display:column>
		
		<display:column titleKey="fixUpTask.edit">
			<a href="quolet/customer/edit.do?quoletId=${row.id}">
				<spring:message code="fixUpTask.edit" /></a>
		</display:column>
		
		<display:column titleKey="fixUpTask.delete">
			<a href="quolet/customer/delete.do?quoletId=${row.id}">
				<spring:message code="fixUpTask.delete" /></a>
		</display:column>
		</display:table>

		<input type="button" name="create" value="<spring:message code="quolet.create" />"
		onclick="javascript: relativeRedir('quolet/customer/create.do?fixUpTaskId=${fixUpTaskId}');" />
        <br />
    </jstl:otherwise>
</jstl:choose>

<input type="button" name="Volver" 
	value="<spring:message code="quolet.volver" />" 
	onclick="javascript:relativeRedir('fix-up-task/customer/list.do');"/>
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">
	
<jstl:choose>
    <jstl:when test="${not empty error}">
    
        <p style="color:red"> <spring:message code="quolet.error" /> </p>
        
    </jstl:when>    
    <jstl:otherwise>
        <p><spring:message code="quolet.list" /></p>
		<display:table pagesize="5" name="quolets" id="row" requestURI="${requestURI}" >
			<display:column property="ticker" titleKey="quolet.ticker" />
			<display:column property="moment" titleKey="quolet.moment" />
			<display:column property="body" titleKey="quolet.body" />
			<display:column property="picture" titleKey="quolet.picture" />	
			
			<display:column titleKey="fixUpTask.show">
				<a href="quolet/handy-worker/show.do?quoletId=${row.id}">
					<spring:message code="fixUpTask.show" /></a>
		</display:column>
		</display:table>

    </jstl:otherwise>
</jstl:choose>

<input type="button" name="Volver" 
	value="<spring:message code="quolet.volver" />" 
	onclick="javascript:relativeRedir('fix-up-task/handy-worker/list.do');"/>
</security:authorize>


