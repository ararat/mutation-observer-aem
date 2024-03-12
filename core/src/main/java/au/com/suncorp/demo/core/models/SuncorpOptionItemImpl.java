package au.com.suncorp.demo.core.models;

import com.adobe.cq.wcm.core.components.models.form.OptionItem;
import com.day.cq.wcm.foundation.forms.FormsHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;


@Slf4j
public class SuncorpOptionItemImpl implements OptionItem {

    private static final String PN_TEXT = "text";
    private static final String PN_SELECTED = "selected";
    private static final String PN_DISABLED = "disabled";
    private static final String PN_VALUE = "value";
    private static final String PN_DATA1 = "data1";

    private SlingHttpServletRequest request;
    private Resource options;
    private ValueMap properties;

    public SuncorpOptionItemImpl(SlingHttpServletRequest request, Resource options, Resource option) {
        this.request = request;
        this.options = options;
        this.properties = option.getValueMap();
    }

    public String getData1() {
        return properties.get(PN_DATA1, String.class);
    }

    @Override
    public String getText() {
        return properties.get(PN_TEXT, String.class);
    }

    @Override
    public boolean isSelected() {
        String[] prefillValues = FormsHelper.getValues(request, options);
        if (prefillValues != null) {
            for (String prefillValue : prefillValues) {
                if (prefillValue.equals(this.getValue())) {
                    return true;
                }
            }
            return false;
        }
        return properties.get(PN_SELECTED, false);
    }

    @Override
    public boolean isDisabled() {
        return properties.get(PN_DISABLED, false);
    }

    @Override
    public String getValue() {
        return properties.get(PN_VALUE, String.class);
    }

}
