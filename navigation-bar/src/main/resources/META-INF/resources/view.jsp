<%@ include file="/init.jsp" %>
<%
final String navigation = ParamUtil.getString(request, "navigation", "foo");

NavigationBarDisplayContext displayContext =  (NavigationBarDisplayContext)request.getAttribute("CUSTOM_DISPLAY_CONTEXT");
%>
<clay:navigation-bar inverted="<%= true %>"
	navigationItems="<%=displayContext.getNavItems(pageContext,navigation,request,renderResponse)%>" />


<liferay-util:include page="<%=displayContext.getCurrentPage(navigation)%>" servletContext="<%= application %>" />
