package io.github.prospector.modmenu.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.EntryListWidget;

public class DescriptionListWidget extends EntryListWidget<DescriptionListWidget.DescriptionItem> {

	private final ModListScreen parent;
	private final TextRenderer textRenderer;
	private ModListEntry lastSelected = null;

	public DescriptionListWidget(MinecraftClient client, int width, int height, int top, int bottom, int entryHeight, ModListScreen parent) {
		super(client, width, height, top, bottom, entryHeight);
		this.parent = parent;
		this.textRenderer = client.textRenderer;
	}

	@Override
	public DescriptionItem getSelected() {
		return null;
	}

	@Override
	public int getRowWidth() {
		return this.width - 10;
	}

	@Override
	protected int getScrollbarPosition() {
		return this.width - 6 + left;
	}

	@Override
	public void render(int mouseX, int mouseY, float delta) {
		ModListEntry selectedEntry = parent.getModList().getSelected();
		if (selectedEntry != lastSelected) {
			lastSelected = selectedEntry;
			clearEntries();
			setScrollAmount(-Double.MAX_VALUE);
			String description = lastSelected.getMetadata().getDescription();
			if (lastSelected != null && description != null && !description.isEmpty()) {
				for (String line : textRenderer.wrapStringToWidthAsList(description.replaceAll("\n", "\n\n"), getRowWidth())) {
					children().add(new DescriptionItem(line));
				}
			}
		}
		super.render(mouseX, mouseY, delta);
	}

	protected class DescriptionItem extends EntryListWidget.Entry<DescriptionItem> {
		protected String text;

		public DescriptionItem(String text) {
			this.text = text;
		}

		@Override
		public void render(int index, int y, int x, int itemWidth, int itemHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
			MinecraftClient.getInstance().textRenderer.drawWithShadow(text, x, y, 0xAAAAAA);
		}
	}

}
