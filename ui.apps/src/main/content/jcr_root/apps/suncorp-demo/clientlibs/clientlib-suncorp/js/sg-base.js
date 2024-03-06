const sg_aem_global = {
    init: function() {
        console.log("Found elements", elements.length);
        [].forEach.call(elements, function (element) {
            console.log("Adding mutation observer to element", element);
            const observer = new MutationObserver(sg_aem_global.onMutate);
            const config = {attributes: true, childList: false, characterData: false, subtree: false};
            observer.observe(element, config);
        
            element.setAttribute('data-loaded', 'true');
        });
    },
    onMutate: function(mutationsList, observer) {
        for (let mutation of mutationsList) {
            const target = mutation.target;
            if (mutation.attributeName === 'style' && target.getAttribute('data-loaded') === 'true') {
                return;
            }
            const data = JSON.parse(target.getAttribute("data-data"));
            const type = target.getAttribute("data-component");
            let newTarget = sg_aem_global[type].assemble(target, data);
            sg_aem_global.render(target, newTarget);
        }
    },
    render: function(ref, newTarget) {
        ref.innerHTML = newTarget;
        ref.setAttribute('style', '');
        ref.setAttribute('aria-hidden', '');
    }
};

const elements = document.getElementsByClassName("sg-aem-component");
if (elements.length > 0) {
    sg_aem_global.init(elements);
}