import {ReactDOM} from 'react-dom';
import {React} from 'react';

const propProvider = document.getElementsByClassName("aem-radioButton")[0];
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

class RadioBtn extends React.Component {

    // React components are simple functions that take in props and state, and render HTML
    render() {
        propProvider.style = '';
        // noinspection JSXUnresolvedComponent
        return ReactDOM.createPortal(
            <SGReact.RadioGroup name="radios" appearance="default" value="first" width="200px" onChange={(value) => {
                console.log(`value ${value}`);
            }} defaultValue="first">
                <SGReact.RadioItem id="radio-item-1" value="first">{content.title}</SGReact.RadioItem>
                <SGReact.RadioItem id="radio-item-2" value="second">{content.desc}</SGReact.RadioItem>
                <SGReact.RadioItem id="radio-item-3" value="third">{content.offer.title}</SGReact.RadioItem>
                <SGReact.RadioItem id="radio-item-4" value="fourth">
                    {content.offer.desc}
                </SGReact.RadioItem>
                <SGReact.RadioItem id="radio-item-5" value="fifth"><img src={content.icon}/></SGReact.RadioItem>
                <SGReact.RadioItem id="radio-item-6" value="sixth" disabled>Sixth</SGReact.RadioItem>
            </SGReact.RadioGroup>,
            document.getElementById('test1')
        );
    }
}

ReactDOM.render(<RadioBtn/>, document.getElementsByClassName("aem-radioButton"));
