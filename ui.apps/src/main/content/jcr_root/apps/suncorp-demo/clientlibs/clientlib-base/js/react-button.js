const propProvider = document.getElementById("test1");
let content = {
    title: '',
    desc: '',
    offer: {
        title: '',
        desc: ''
    },
    icon: '',
    dialog: false,
    size: ''
}
content.title = propProvider.getAttribute('data-title');
content.desc = propProvider.getAttribute('data-desc');
content.icon = propProvider.getAttribute('data-icon');
content.dialog = propProvider.hasAttribute('data-dialog');
content.size = propProvider.getAttribute('data-radio-size');
if (propProvider.hasAttribute('data-offer-title')) {
    content.offer.title = propProvider.getAttribute('data-offer-title');
    content.offer.desc = propProvider.getAttribute('data-offer-desc');
}

class SimpleExample extends React.Component {

    // React components are simple functions that take in props and state, and render HTML
    render() {
        propProvider.style = '';
        return ReactDOM.createPortal(
            <div className="modal">
                <p>{content.title}</p>
            </div>,
            document.getElementById('test1')
        );
    }
}

ReactDOM.render(<SimpleExample/>, document.getElementById('test1'));
