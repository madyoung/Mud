/**
 * 
 */
package com.mud.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenzhiwei
 * @since 2014-12-9 下午5:51:29
 */
public class GameContext {

	public static Map<String, GameMap> maps = new HashMap<String, GameMap>();

	{
		List<GameMap> maps = new ArrayList<GameMap>();
		maps.add(new GameMap("m1", "生科院正门"));
		maps.add(new GameMap("m2", "生科院后门"));
		maps.add(new GameMap("m3", "生科院前走廊"));
		maps.add(new GameMap("m4", "生科院后走廊"));
		maps.add(new GameMap("m5", "生科院配电室"));
		maps.add(new GameMap("m6", "苏丹黄办公室"));
		maps.add(new GameMap("m7", "苏丹红办公室"));
		maps.add(new GameMap("m8", "实验室"));

		List<Item> items = new ArrayList<Item>();
		items.add(new Item("i1", "汽车"));
		items.add(new Item("i1", "门锁"));
		items.add(new Item("i1", "水桶"));
		items.add(new Item("i1", "配电箱"));
		items.add(new Item("i1", "抽屉"));
		items.add(new Item("i1", "桌子"));
		items.add(new Item("i1", "柜子"));
		items.add(new Item("i1", "电话"));

		List<Scene> scenes = new ArrayList<Scene>();
	}
}
