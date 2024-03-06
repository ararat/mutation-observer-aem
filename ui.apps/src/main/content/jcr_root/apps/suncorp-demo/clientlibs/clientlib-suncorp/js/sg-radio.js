sg_aem_global['radio'] = {
    data: {},
    // TODO: Move function to base
    assemble: function(el, data) {
        let newTarget = sg_aem_global.radio.populateTemplate(data);
        return newTarget;
    },
    // Pre-defined templates for each component
    // Pre-defined expected set of data properties
    // Check all data properties needed are coming through
    // Set conditional rendering depending on properties

    // QUESTIONS:
    // 1. Will always need a parent? Can't replace itself because then the reference is lost
    //    This could be problematic in for example a radio group, where radio buttons are
    //    expected to be direct children.
    //    Possible solution: Always make the top element in the HTML template the correct element
    //    and modify it instead of replacing it.
    populateTemplate: function(data) {
        const radioTemplate = `
            <label id="radio-${data.id}" class="sg-aem-component sg-Radio sg-Radio-${data.type}">
                <input type="radio" name="radiocheck-complexRadioButtons" class="sg-Radio-input">
                <i class="sg-Radio-icon"></i>
                <div class="sg-Radio-text sg-u-textCenter">
                <i class="Icon-${data.icon}--dark Icon--large Icon--${data.iconSize ? data.iconSize : 'Icon--large'}"></i>
                    <div>
                        <span class="sg-Type--bold">${data.title}</span>
                    </div>
                    <div>
                        <span>
                            ${data.desc ? data.desc : ""}
                        </span>
                    </div>
                </div>
            </label>
        `;
        return radioTemplate;
    }
}