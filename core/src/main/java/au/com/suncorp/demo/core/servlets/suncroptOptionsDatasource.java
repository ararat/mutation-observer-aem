package au.com.suncorp.demo.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component(service = {Servlet.class}, property = {"sling.servlet.methods=GET", "sling.servlet.paths=/bin/suncorp-demo/components/form/options/datasources/sizeoptions"})
@ServiceDescription("Datasource demo for Suncorp")
public class suncroptOptionsDatasource extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        List<Resource> fakeResourceList = new ArrayList<Resource>();
        ValueMap vm = new ValueMapDecorator(new HashMap<String, Object>());
        vm.put("value", "1");
        vm.put("text", "1");
        fakeResourceList.add(new ValueMapResource(request.getResourceResolver(), new ResourceMetadata(), "nt:unstructured", vm));

        vm = new ValueMapDecorator(new HashMap<String, Object>());
        vm.put("value", "2");
        vm.put("text", "2");
        fakeResourceList.add(new ValueMapResource(request.getResourceResolver(), new ResourceMetadata(), "nt:unstructured", vm));

        vm = new ValueMapDecorator(new HashMap<String, Object>());
        vm.put("value", "3");
        vm.put("text", "3");
        fakeResourceList.add(new ValueMapResource(request.getResourceResolver(), new ResourceMetadata(), "nt:unstructured", vm));

        //Create a DataSource that is used to populate the drop-down control
        DataSource ds = new SimpleDataSource(fakeResourceList.iterator());
        request.setAttribute(DataSource.class.getName(), ds);
    }
}
