package org.dominokit.domino.ui.lists;

import elemental2.dom.HTMLLIElement;
import elemental2.dom.Node;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.utils.HasBackground;
import org.jboss.gwt.elemento.core.IsElement;

import static java.util.Objects.nonNull;
import static org.jboss.gwt.elemento.core.Elements.li;

public class SimpleListItem extends BaseListItem<HTMLLIElement, SimpleListItem> implements HasBackground<SimpleListItem> {

    private String style;
    private HTMLLIElement element= li()
            .css(ListStyles.LIST_GROUP_ITEM)
            .asElement();

    public SimpleListItem() {
        setElement(element);
        init(this);
    }

    public static SimpleListItem create(String text) {
        SimpleListItem simpleListItem = new SimpleListItem();
        simpleListItem.element.textContent = text;
        return simpleListItem;
    }

    public SimpleListItem setStyle(ListGroupStyle itemStyle) {
        return setStyle(itemStyle.getStyle());
    }

    private SimpleListItem setStyle(String itemStyle) {
        if (nonNull(this.style)) {
            style().remove(this.style);
        }
        style().add(itemStyle);
        this.style = itemStyle;
        return this;
    }

    @Override
    public SimpleListItem setBackground(Color background) {
        setStyle(background.getBackground());
        return this;
    }

    public SimpleListItem setHeading(String heading) {
        setHeaderText(heading);
        return this;
    }

    public SimpleListItem setText(String content) {
        setBodyText(content);
        return this;
    }

    public SimpleListItem appendChild(Badge badge){
        appendChild(badge, true);
        return this;
    }

    public SimpleListItem appendChild(Badge badge, boolean first){
        if(first) {
            insertFirst(badge);
        }else{
            appendChild(badge.asElement());
        }
        return this;
    }

    public SimpleListItem appendChild(Node content) {
        asElement().appendChild(content);
        return this;
    }

    public SimpleListItem appendChild(IsElement content) {
       return appendChild(content.asElement());
    }

}