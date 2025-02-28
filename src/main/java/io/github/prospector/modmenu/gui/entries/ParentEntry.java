package io.github.prospector.modmenu.gui.entries;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.prospector.modmenu.ModMenu;
import io.github.prospector.modmenu.gui.ModListEntry;
import io.github.prospector.modmenu.gui.ModListWidget;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ParentEntry extends ModListEntry {
	private static final Identifier PARENT_MOD_TEXTURE = new Identifier(ModMenu.MOD_ID, "textures/gui/parent_mod.png");
	protected List<ModContainer> children;
	protected ModListWidget list;
	protected boolean hoveringIcon = false;

	public ParentEntry(ModContainer parent, List<ModContainer> children, ModListWidget list) {
		super(parent, list);
		this.list = list;
	}

	@Override
	public void render(int index, int y, int x, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
		super.render(index, y, x, rowWidth, rowHeight, mouseX, mouseY, isSelected, delta);
		this.hoveringIcon = mouseX >= x - 1 && mouseX <= x - 1 + 32 && mouseY >= y - 1 && mouseY <= y - 1 + 32;
		if (isMouseOver(mouseX, mouseY)) {
			DrawableHelper.fill(x, y, x + 32, y + 32, 0xA0909090);
			this.client.getTextureManager().bindTexture(PARENT_MOD_TEXTURE);
			int xOffset = list.getParent().showModChildren.contains(getMetadata().getId()) ? 32 : 0;
			int yOffset = hoveringIcon ? 32 : 0;
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			DrawableHelper.blit(x, y, xOffset, yOffset, 32 + xOffset, 32 + yOffset, 256, 256);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int i) {
		if (hoveringIcon) {
			String id = getMetadata().getId();
			if (list.getParent().showModChildren.contains(id)) {
				list.getParent().showModChildren.remove(id);
			} else {
				list.getParent().showModChildren.add(id);
			}
			list.filter(list.getParent().getSearchInput(), false);
		}
		return super.mouseClicked(mouseX, mouseY, i);
	}

	@Override
	public boolean keyPressed(int int_1, int int_2, int int_3) {
		if (int_1 == GLFW.GLFW_KEY_ENTER) {
			String id = getMetadata().getId();
			if (list.getParent().showModChildren.contains(id)) {
				list.getParent().showModChildren.remove(id);
			} else {
				list.getParent().showModChildren.add(id);
			}
			list.filter(list.getParent().getSearchInput(), false);
			return true;
		}
		return super.keyPressed(int_1, int_2, int_3);
	}

	public void setChildren(List<ModContainer> children) {
		this.children = children;
	}

	public void addChildren(List<ModContainer> children) {
		this.children.addAll(children);
	}

	public void addChildren(ModContainer... children) {
		this.children.addAll(Arrays.asList(children));
	}

	public List<ModContainer> getChildren() {
		return children;
	}

	public boolean isMouseOver(double double_1, double double_2) {
		return Objects.equals(this.list.getEntryAtPos(double_1, double_2), this);
	}
}
