package au.com.suncorp.demo.core.models;

import com.adobe.cq.wcm.core.components.models.form.Options;
import lombok.Getter;
import lombok.experimental.Delegate;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;


@Model(adaptables = SlingHttpServletRequest.class, adapters = Options.class, resourceType = "suncorp-demo/components/formOptions")
public class suncorpOptions implements Options {

    @Self //resolve to current resource
    @Via(type = ResourceSuperType.class) //type of resource we are to resolve to which is our supertype
    @Delegate //delegate all method calls to the private object Options
    private Options delegate;

    @Getter
    @ValueMapValue
    public String variant;

}
