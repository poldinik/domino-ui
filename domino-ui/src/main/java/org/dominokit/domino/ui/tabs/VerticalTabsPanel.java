package org.dominokit.domino.ui.tabs;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.animations.Animation;
import org.dominokit.domino.ui.animations.Transition;
import org.dominokit.domino.ui.grid.flex.FlexItem;
import org.dominokit.domino.ui.grid.flex.FlexLayout;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.style.Style;
import org.dominokit.domino.ui.utils.BaseDominoElement;
import org.dominokit.domino.ui.utils.DominoElement;
import org.jboss.gwt.elemento.core.IsElement;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.dominokit.domino.ui.style.Unit.px;
import static org.jboss.gwt.elemento.core.Elements.div;

public class VerticalTabsPanel extends BaseDominoElement<HTMLDivElement, VerticalTabsPanel> {

    private final VTabsContainer tabsList = VTabsContainer.create();
    private final FlexItem tabsHeadersContainer;
    private DominoElement<HTMLDivElement> element = DominoElement.of(div().css("vtabs-panel"));
    private HTMLElement tabsContent = div().css("tab-content").asElement();
    private VerticalTab activeTab;
    private Color tabsColor;
    private Transition transition;
    private List<VerticalTab> tabs = new ArrayList<>();
    private Color background;

    public static VerticalTabsPanel create() {
        return new VerticalTabsPanel();
    }

    public VerticalTabsPanel() {

        tabsHeadersContainer = FlexItem.create();
        element.appendChild(FlexLayout.create()
                .styler(style -> style.add("tabs-container"))
                .appendChild(tabsHeadersContainer
                        .styler(style -> style.add("tabs"))
                        .appendChild(tabsList))
                .appendChild(FlexItem.create()
                        .styler(style -> style
                                .add("tabs-content")
                                .setProperty("border-left", "1px solid #cecece")
                        )
                        .setFlexGrow(1)
                        .appendChild(tabsContent))
        );

        init(this);
        setColor(Color.BLUE);
    }

    public VerticalTabsPanel appendChild(FillItem fillItem) {
        if(nonNull(fillItem)){
            tabsList.appendChild(fillItem);
        }
      return this;
    }


    public VerticalTabsPanel appendChild(VerticalTab tab) {
        if (nonNull(tab)) {
            tabs.add(tab);
            if (isNull(activeTab)) {
                this.activeTab = tab;
                this.activeTab.activate();
            } else {
                if (tab.isActive()) {
                    activateTab(tab);
                }
            }
            tabsList.appendChild(tab);
            tabsContent.appendChild(tab.getContentContainer().asElement());
            tab.getClickableElement().addEventListener("click", evt -> activateTab(tab));
        }
        return this;
    }

    public void activateTab(int index) {
        if (!tabs.isEmpty() && index < tabs.size() && index >= 0) {
            activateTab(tabs.get(index));
        } else {
            throw new IndexOutOfBoundsException("provided index of [" + index + "] is not within current tabs of size [" + tabs.size() + "].");
        }
    }

    public void activateTab(VerticalTab tab) {
        if (nonNull(tab) && tabs.contains(tab)) {
            activeTab.deactivate();
            activeTab = tab;
            activeTab.activate();

            if (nonNull(transition)) {
                Animation.create(activeTab.getContentContainer())
                        .transition(transition)
                        .animate();
            }
        }
    }

    public VerticalTabsPanel setColor(Color color) {
        if (nonNull(this.tabsColor)) {
            tabsList.style().remove(tabsColor.getStyle());
        }
        tabsList.style().add(color.getStyle());
        this.tabsColor = color;
        return this;
    }

    public VerticalTabsPanel setBackgroundColor(Color background) {
        if (nonNull(this.background)) {
            tabsList.style().remove(this.background.getBackground());
        }
        tabsList.style().add(background.getBackground());
        this.background = background;
        return this;
    }

    @Override
    public HTMLDivElement asElement() {
        return element.asElement();
    }

    public VerticalTabsPanel setTransition(Transition transition) {
        this.transition = transition;
        return this;
    }

    public VerticalTabsPanel setContentContainer(HTMLElement contentContainer) {
        if (element.contains(tabsContent)) {
            tabsContent.remove();
        }
        Style.of(contentContainer).add("tab-content");
        this.tabsContent = contentContainer;
        return this;
    }

    public VerticalTabsPanel setContentContainer(IsElement contentContainer) {
        return setContentContainer(contentContainer.asElement());
    }

    public DominoElement<HTMLElement> getTabsContent() {
        return DominoElement.of(tabsContent);
    }

    public VerticalTab getActiveTab() {
        return activeTab;
    }

    public List<VerticalTab> getTabs() {
        return tabs;
    }

    public VerticalTabsPanel textBelowIcon() {
        tabsList.style().add("text-below");
        return this;
    }

    public VerticalTabsPanel textBesideIcon() {
        tabsList.style().remove("text-below");
        return this;
    }

    public FlexItem getTabsHeadersContainer() {
        return tabsHeadersContainer;
    }
}
