/**
 * 
 */
package com.mud.game;

/**
 * @author chenzhiwei
 * @since 2015-1-6
 */
public class MenuFactory {
	public static final String CMD_GAME_SELECT_MENU = "game_select_menu";
	public static final String CMD_GAME_MAIN_MENU = "game_main_menu";
	public static final String CMD_MESSAGE_MENU = "message_menu";
	
	public static final String CMD_START_GAME = "start_game_menu";

	private static Menu createMainMenuJiongZhenTanFC() {
		Menu gameMainMenu = new MessageMenu("1", "囧侦探FC-暗夜中的吞噬者");
		gameMainMenu.setMessage("天上一道光芒照射下来，有点晃眼。你睁开眼，已然来到了囧侦探FC的世界。请输入数字做出你的选择：");

		DialogMenu start = new DialogMenu("1", "新开始游戏");
		Menu load = new MessageMenu("2", "读取最近游戏存档");
		Menu best = new MessageMenu("3", "查看自己最好成绩");
		Menu exit = new MessageMenu("4", "退出囧侦探FC的世界");
		Menu not = new MessageMenu("5", "千万不要选这个！");
		gameMainMenu.addMenu(start);
		gameMainMenu.addMenu(load);
		gameMainMenu.addMenu(best);
		gameMainMenu.addMenu(exit);
		gameMainMenu.addMenu(not);

		start.appendDialog("当社会变得物欲横流，当人们失去信任与真诚，当生活被重重谎言所包围……\n（输入任意内容继续）");
		start.appendDialog("世界，是否已经走到尽头？未必！\n（输入任意内容继续）");
		start.appendDialog("传说，世界的奥义沉睡在一个字里，它生动，它神秘，它无所不包，它无处不在，它是希望，它是图腾。\n（输入任意内容继续）");
		start.appendDialog("它是上苍赐给人类最后的救赎，它蕴藏在八个代表着世间终极智慧的神物中，指引着被命运选中的少年穿梭于死亡与黑暗的边缘，狂奔在疯狂与悲哀的世界，探索者晃眼背后永恒的真相。那就是……\n（输入任意内容继续）");
		start.appendDialog("囧！\n为了世界的囧，现在就行动起来吧，被选中的少年，校园的明星——囧侦探！\n（输入任意内容继续）");

		DialogMenu jiongDiZhiZhi = new DialogMenu("", "");
		start.addMenu(jiongDiZhiZhi);

		jiongDiZhiZhi.appendDialog("奉天承运，囧帝诏曰：\n" +
				"任命囧大学生科北为御前天字第一号囧侦探！\n" +
				"附录：科北简历如下。\n" +
				"姓名：科北\n" +
				"性别：男\n" +
				"就读院校：囧大\n" +
				"专业：生物技术基地班\n" +
				"学历：本科在校\n" +
				"任命职务：天字第一号囧侦探\n" +
				"爱好：睡觉\n" +
				"特点：人格严重囧化，时而以为自己是科比，时而以为自己是柯南，即多重人格\n" +
				"签署人：囧帝\n" +
				"（输入任意内容继续）\n");
		
		return gameMainMenu;
	}

	public static Menu createGameSelectMenu() {
		Menu gameSelectMenu = new MessageMenu("", "");
		gameSelectMenu.setMessage("欢迎来到囧雷游戏世界，请输入数字选择你要进行的游戏：");
		gameSelectMenu.addMenu(createMainMenuJiongZhenTanFC());
		gameSelectMenu.addMenu(new MessageMenu("2", "查看制作人员名单"));
		return gameSelectMenu;
	}

}
