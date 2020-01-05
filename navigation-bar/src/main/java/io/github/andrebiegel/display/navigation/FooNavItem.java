package io.github.andrebiegel.display.navigation;

import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = NavigationItemProvider.class)
public class FooNavItem implements NavigationItemProvider {

    @Override
    public UnsafeConsumer<NavigationItem, Exception> item(final String activeEntry, final HttpServletRequest request,
            final RenderResponse renderResponse) {
         return navigationItem -> {
            navigationItem.setActive(activeEntry.equals("foo"));
            navigationItem.setHref(renderResponse.createRenderURL(),"navigation", "foo");
            navigationItem.setLabel(LanguageUtil.get(request, "foo"));
        };
    }

    @Override
    public String getPage(String navigation) {
       
        return navigation.equals("foo")?"/foo.jsp": "/empty.jsp";
    }
}