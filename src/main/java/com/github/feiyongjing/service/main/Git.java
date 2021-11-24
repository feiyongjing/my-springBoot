package com.github.feiyongjing.service.main;

import com.github.feiyongjing.service.CommandTest;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Git extends CommandTest {
    public static void main(String[] args){
        CommandTest commandTest=new Git();
        commandTest.commandInputOutput(commandTest.getQuestionsAndAnswers());
    }

    @Override
    public HashMap<String, String> getQuestionsAndAnswers() {
        LinkedHashMap<String, String> questionsAndAnswers = new LinkedHashMap<>();
        questionsAndAnswers.put("新建一个目录，将其初始化为Git代码库", "git init [targetDir]");
        questionsAndAnswers.put("下载一个项目和它的整个代码历史??", "git clone [url]");
        questionsAndAnswers.put("下载远程仓库的所有变动", "git fetch");
        questionsAndAnswers.put("添加指定文件到暂存区 ", "git add [targetDir]");
        questionsAndAnswers.put("将选择的文件提交到本地", "git commit -m [message]");
        questionsAndAnswers.put("取回远程仓库的变化，并与本地分支合并", "git pull [origin] [branch]");
        questionsAndAnswers.put("上传本地指定分支到远程仓库 ", "git push [-u] [origin] [branch]");
        questionsAndAnswers.put("查看当下状态下所有被提交的文件", "git log");
        questionsAndAnswers.put("查看当前分支状态", "git status");

        questionsAndAnswers.put("指定文件从暂存区移除(用于删除错误添加的文件)", "git reset HEAD [targetDir]");
        questionsAndAnswers.put("改名文件，并且将这个改名放入暂存区", "git mv [sourceDir] [targetDir]");
        questionsAndAnswers.put("删除工作区文件，并且将这次删除放入暂存区", "git rm [-r targetDir]");
        questionsAndAnswers.put("停止指定文件的版本控制，但该文件会保留在工作区", "git rm --cached [targetDir]??");
        questionsAndAnswers.put("将本次提交合并到上次提交中去", "git commit '\'-\\-amend??");
        questionsAndAnswers.put("commit提交取消某一次commit文件产生的变化", "git revert [commitCode]");

        questionsAndAnswers.put("commit提交某一次commit提交的文件变化", "git cherry-pick [commitCode]");

        questionsAndAnswers.put("列出所有本地或远程分支??", "git branch [-ar]");
        questionsAndAnswers.put("新建一个分支，但依然停留在当前分支", "git branch [newBranch]");
        questionsAndAnswers.put("建立追踪关系，在现有分支与指定的远程分支之间", "git branch --set-upstream [branch] [remote-branch]");
        questionsAndAnswers.put("删除分支", "git branch [-d branch]??");
        questionsAndAnswers.put("新建一个分支，与指定的远程分支建立追踪关系", "git branch --track [branch] [remote-branch]??");
        questionsAndAnswers.put("切换到上一个分支 ", "git checkout -");
        questionsAndAnswers.put("切换到指定分支或历史，并更新工作区", "git checkout [branch | historyCode]");
        questionsAndAnswers.put("新建一个分支，并切换到该分支", "git checkout [-b newBranch]");
        questionsAndAnswers.put("回退到指定历史(完全一致,会删除现有的暂存区和工作区)", "git reset --hard [historyCode]");
        questionsAndAnswers.put("回退到指定历史(会保留现有的暂存区和工作区，但是暂存区同时也有历史)", "git reset --soft [historyCode]");
        questionsAndAnswers.put("回退到指定历史(工作区会和历史混合,同时清除空现有的暂存区)", "git reset --mixed [historyCode]");
        questionsAndAnswers.put("基于历史新建一个分支，并切换到该分支", "git checkout [-b newBranch] [historyCode]");


        questionsAndAnswers.put("合并指定分支到当前分支", "git merge [branch]");
        questionsAndAnswers.put("合并导致冲突在解决冲突后,继续合并分支", "git merge --continue");
        questionsAndAnswers.put("合并导致冲突后,停止合并分支回到合并之前的状态", "git merge --abort");
        questionsAndAnswers.put("合并导致冲突后,引起冲突的commits会被丢弃(不要使用)", "git merge --skip");
        questionsAndAnswers.put("指定分支的提交压扁后合并到当前分支", "git merge --squash [branch]");
        questionsAndAnswers.put("当前分支与指定分支历史的分叉之后的当前分支所有提交在指定分支的尖端全部重新提交一次", "git rebase [branch]");
        questionsAndAnswers.put("中断rebase操作并回到rebase之前的状态", "git rebase --abort");
        questionsAndAnswers.put("git rebase 发生冲突，冲突解决并git add 后继续进行rebase操作", "git rebase --continue");
        questionsAndAnswers.put("git rebase 导致冲突后,引起冲突的commits会被丢弃(不要使用)", "git rebase --skip");

//        questionsAndAnswers.put("进入bisect模式进行debug", "git bisect start");
//        questionsAndAnswers.put("退出bisect模式", "git bisect reset");
//        questionsAndAnswers.put("当前状态是坏的", "git bisect bad");
//        questionsAndAnswers.put("当前状态是好的", "git bisect good");
//        questionsAndAnswers.put("当前状态不确定时,跳过当前状态", "git bisect skip");
//        questionsAndAnswers.put("启动指定的shell脚本进行debug(注意要先进入bisect模式并确定好的与坏的之间的区间)", "git bisect run [shellFile]");

//        questionsAndAnswers.put("列出所有tag", "git tag");
//        questionsAndAnswers.put("新建一个tag在当前commit", "git tag [v1.0]");
//        questionsAndAnswers.put("新建一个tag在当前commit并附加tag信息", "git tag [-a v1.0] [-m massage]");
//        questionsAndAnswers.put("新建一个tag在指定commit", "git tag [v1.0] [commitCode]");
//        questionsAndAnswers.put("删除本地tag", "git tag [-d v1.0]");
//        questionsAndAnswers.put("删除远程tag", "git push origin :refs/tags/[v1.0]");
//        questionsAndAnswers.put("查看指定tag信息", "git show [v1.0]");
//        questionsAndAnswers.put("提交指定tag", "git push origin [v1.0]");
//        questionsAndAnswers.put("提交全部的tag", "git push origin --tags");
//        questionsAndAnswers.put("新建一个分支，指向某个tag", "git checkout -b [branch] [v1.0]");
//        questionsAndAnswers.put("更新本地tag", "fetch --tags");


        questionsAndAnswers.put("强行推送当前分支到远程仓库，即使有冲突", "git push [-f origin]");
        questionsAndAnswers.put("推送所有分支到远程仓库", "git push [origin] --all??");
        questionsAndAnswers.put("显示所有远程仓库", "git remote -v");
        questionsAndAnswers.put("显示某个远程仓库的信息", "git remote show [remote-repository]");
        questionsAndAnswers.put("增加一个新的远程仓库，并命名", "git remote add [remoteName] [url]");
        return questionsAndAnswers;
    }
}
