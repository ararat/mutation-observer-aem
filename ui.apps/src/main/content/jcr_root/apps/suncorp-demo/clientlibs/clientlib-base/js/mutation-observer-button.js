const elements = document.getElementsByClassName("aem-radioButton");

[].forEach.call(elements, function (element) {
    const observer = new MutationObserver(callback);
    const config = {attributes: true, childList: false, characterData: false, subtree: false};
    observer.observe(element, config);
    console.log(config);
    element.setAttribute('data-loaded', 'true');
});


function populateContent(content) {
    console.log(content)
    const radioText = document.createElement('div');
    radioText.className = 'sg-Radio-text';
    const radioTextIcon = document.createElement('i');
    const radioTextContentIcon = document.createElement('i');
    radioTextIcon.className = `sg-Radio-contentIcon Icon-${content.icon}--dark Icon--xlarge`;
    radioTextContentIcon.className = `sg-Radio-contentIcon--checked Icon-${content.icon}--dark Icon--xlarge`;
    radioText.append(radioTextIcon);
    radioText.append(radioTextContentIcon);
    if (content.desc) {
        const title = document.createElement('strong');
        title.className = 'sg-Type--prominent sg-u-marginBottom';
        title.textContent = content.title;
        const contentBlock = document.createElement('div');
        contentBlock.className = 'sg-Type--size14 sg-u-inlineBlock';
        const text = document.createElement('p');
        text.textContent = content.desc;
        contentBlock.append(text);
        if (content.offer.title) {
            const inlineBlock = document.createElement('div');
            inlineBlock.className = 'sg-u-textCentre sg-u-inlineBlock sg-u-noMargin';
            const offerContentBlock = document.createElement('p');
            offerContentBlock.className = 'sg-u-inlineBlock sg-u-widthSmall';
            const offerTitle = document.createElement('span');
            offerTitle.id = 'subStrikethroughText';
            offerTitle.className = 'sg-Type--strikeBehind sg-u-borderWidth2 sg-u-borderText sg-Type--title sg-Type--size14 sg-u-marginTop sg-u-noMarginBottom';
            console.log(content.offer.title);
            offerTitle.textContent = content.offer.title;
            const offerDesc = document.createElement('span');
            offerDesc.textContent = content.offer.desc;
            offerContentBlock.append(offerTitle);
            offerContentBlock.append(offerDesc);
            inlineBlock.append(offerContentBlock);
            contentBlock.append(inlineBlock);
            console.log(contentBlock);
            radioText.append(title);
            radioText.append(contentBlock)
        }
    } else {
        const contentBlock = document.createElement('span');
        contentBlock.className = 'sg-u-noMargin sg-Type--prominent';
        contentBlock.textContent = content.title;
        radioText.append(contentBlock);
    }

    return radioText;
}

// Define the callback function to be executed on mutations
function callback(mutationsList, observer) {
    // Batch process mutations
    for (let mutation of mutationsList) {
        const target = mutation.target;
        if (mutation.attributeName === 'style' && target.getAttribute('data-loaded') === 'true') {
            return;
        }
        const type = target.getAttribute('data-type');
        const parent = target.parentNode;
        console.log(parent);
        parent.innerHtml = ''
        parent.append(target);
        const label = document.createElement('label');
        let content = {
            title: '',
            desc: '',
            offer: {
                title: '',
                desc: ''
            },
            icon: ''
        }

        label.className = 'sg-Radio sg-Radio-btn sg-Grid-col6--medium';
        content.title = target.getAttribute('data-title');
        content.desc = target.getAttribute('data-desc');
        content.icon = target.getAttribute('data-icon')
        if (target.hasAttribute('data-offer-title')) {
            content.offer.title = target.getAttribute('data-offer-title');
            content.offer.desc = target.getAttribute('data-offer-desc');
        }
        console.log(content);
        const input = document.createElement('input');
        input.setAttribute('type', 'radio');
        input.setAttribute('name', 'radiocheck-complexRadioButtons');
        input.setAttribute('aria-describedby', 'sgdocs-dialog-button-tooltip6');
        input.className = 'sg-Radio-input';
        label.append(input);
        const info = document.createElement('i');
        info.className = 'sg-Radio-icon';
        label.append(info);
        label.append(populateContent(content));
        parent.append(label);
        target.innerHtml = ''
    }
}
