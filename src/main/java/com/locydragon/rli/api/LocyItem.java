package com.locydragon.rli.api;

import com.locydragon.rli.nms.ItemNBTSetGet;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.LangReader;
import com.locydragon.rli.util.OptionReader;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocyItem {
	private ItemStack buildItem;
	private String id;
	private HashMap<String,List<OptionReader>> skillsMap = new HashMap<>();

	public LocyItem(String id, String name, Material material) {
		buildItem = new ItemStack(material);
		ItemMeta meta = getMeta();
		meta.setDisplayName(Colors.color(name));
		setMeta(meta);
		this.id = id;
		this.buildItem = ItemNBTSetGet.addPluginTag(this.buildItem, id);
	}

	public ItemStack getItem() {
		return this.buildItem;
	}

	public String getID() {
		return this.id;
	}

	public void setLore(List<String> lore) {
		ItemMeta meta = getMeta();
		meta.setLore(lore);
		setMeta(meta);
	}

	public void enchantment(Enchantment en, int level) {
		this.buildItem.addUnsafeEnchantment(en, level);
	}

	public void addSkill(String param) {
		LangReader langReader = new LangReader(param);
		OptionReader reader = new OptionReader(langReader.value());
		List<OptionReader> readerList = skillsMap.getOrDefault(langReader.headValue().trim().toLowerCase(), new ArrayList<>());
		readerList.add(reader);
		skillsMap.put(langReader.headValue().trim().toLowerCase(), readerList);
	}

	private ItemMeta getMeta() {
		return buildItem.getItemMeta();
	}

	private void setMeta(ItemMeta meta) {
		buildItem.setItemMeta(meta);
	}

	public HashMap<String, List<OptionReader>> getSkillsMap() {
		return this.skillsMap;
	}
}
