package top.aot.cp;

import setting.CopyList;
import top.aot.cp.command.ACPCommand;
import top.aot.cp.command.ACPTCommand;
import top.aot.itf.Main;
import top.aot.plugin.APlugin;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/10/22 21:56
 * @description：
 * @副本图鉴功能：
 * @1：通过命令进行副本图鉴创建
 * @2：配置好的副本直接显示在副本列表内
 * @3：副本图鉴可打开查看副本首页,同时可使用单独的命令打开副本首页
 * @4：副本首页内显示副本详细信息,以及基本的开启要求,包括怪物的击杀序列、击杀数量和奖励物品
 * @5：副本包含信息：副本名称、内部编号、时间单位、单位时间次数、权限要求、等级要求、时间限制
 * @6：、放弃是否返回次数、击杀序列、奖励列表、完成后有领取奖励按钮
 * @7：副本同时只可以开启一个，如有已经开启的副本，则只可打开已开启的副本首页
 * @8：上一个副本奖励未领取时不可开始新的副本，开启的副本随时可以放弃
 * @9：完成副本也可以不领取奖励直接放弃
 * @10：副本击杀序列使用怪物图鉴为基础展示，每个副本最多可设置27个击杀序列
 * @11：完成全部击杀序列后算是完成此副本，有时间限制的副本超时自动放弃副本
 * @12：副本奖励最多可设置18个，可独立设置概率，概率单位万分比，可设置最大奖励数量。
 * @13：完成击杀时聊天栏提示击杀要求和完成情况，达到要求后不再提示。
 * @14：副本奖励类型可设置命令或者物品直接发放奖励。
 */
public enum cpm implements Main {

    A {
        @Override
        public void init() {
            // 重载副本列表
            CopyList.reload();
            APlugin.plugin.getCommand("acp").setExecutor(new ACPCommand());
            APlugin.plugin.getCommand("acpt").setExecutor(new ACPTCommand());
        }
    }
}
