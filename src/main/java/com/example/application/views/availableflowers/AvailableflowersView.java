package com.example.application.views.availableflowers;

import com.example.application.data.entity.Flower;
import com.example.application.data.service.FlowerService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Available flowers")
@Route(value = "flowers", layout = MainLayout.class)
public class AvailableflowersView extends VerticalLayout {

	private Grid<Flower> grid = new Grid<>(Flower.class, false);
	private final FlowerService flowerService;
    public AvailableflowersView(FlowerService flowerService) {
        this.flowerService = flowerService;
		setSpacing(false);

        add(new H2("Available flowers"));
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("color").setAutoWidth(true);
        grid.addColumn("amount").setAutoWidth(true);
        grid.addColumn("price").setAutoWidth(true);
        grid.setItems(flowerService.getFlowers());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        add(grid);

/*        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");*/
    }

}
