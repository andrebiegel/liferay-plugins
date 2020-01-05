package io.github.andrebiegel.display.context;

import java.util.List;
import java.util.Optional;

import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPNavigationItemList;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import io.github.andrebiegel.display.navigation.NavigationItemProvider;

public class NavigationBarDisplayContext {

    private static final String EMPTY_JSP = "/empty.jsp";
    private final List<NavigationItemProvider> providers;

    private static final Log _log = LogFactoryUtil.getLog(
		NavigationBarDisplayContext.class);
    NavigationBarDisplayContext(final List<NavigationItemProvider> providers) {
        this.providers = providers;
    }

    public JSPNavigationItemList getNavItems(final PageContext pageContext, final String navigation,
            final HttpServletRequest request, final RenderResponse renderResponse) {
        final JSPNavigationItemList list = new JSPNavigationItemList(pageContext);
        providers.stream().map(it -> it.item(navigation, request, renderResponse)).forEach(item -> list.add(item));
        return list;
    }

    public String getCurrentPage(final String navigation) {
        Optional<String> activePage = this.providers.stream().map(provider -> provider.getPage(navigation))
                .filter(it -> !it.equals(EMPTY_JSP)).findFirst();
                _log.info("Current Page Found : " + activePage.isPresent());    
        return activePage.orElse(EMPTY_JSP);
    }
}
