package me.metallicgoat.AdvancedSwordsTools.utils;

import java.util.List;

public interface Config {

    List<String> getAntiChest();

    List<String> getAntiDropList();

    boolean getAntiDrop();

    boolean getAdvancedSwordDrop();

    List<String> getSwordDropList();

    boolean getSwordReplace();

    boolean getSwordReplaceType();

    String getToolBuyProblem();

    boolean getToolBuy();

}
