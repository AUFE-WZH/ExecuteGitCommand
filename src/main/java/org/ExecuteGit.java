package org;


import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExecuteGit {
    private String path;
    private List<String> gitCmdExecuteList;
    private List<String> gitCmdResultList;

    public ExecuteGit(String path) {
        this.path = path;
        this.gitCmdExecuteList = new ArrayList<>();
        this.gitCmdResultList = new ArrayList<>();
    }

    public void addGitCmd(String gitCmd) {
        gitCmdExecuteList.add(gitCmd);
    }

    public void clearGitCmd() {
        gitCmdExecuteList = new ArrayList<>();
    }

    public List<String> getGitCmdResultList() {
        return gitCmdResultList;
    }

    public void executeGitCmdExecuteList() {
        String[] command = {"cmd"};
        try {
            Process p = Runtime.getRuntime().exec(command, null, new File(path));
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            for (String str : this.gitCmdExecuteList) {
                stdin.println(str);
            }
            stdin.close();
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = input.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            this.gitCmdResultList.add(sb.toString());

            sb = new StringBuilder();
            while ((line = error.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            this.gitCmdResultList.add(sb.toString());

            p.waitFor();
        } catch (IOException | InterruptedException error) {
            System.out.println("执行出错: " + error);
        }
    }

    @Test
    public void test() {
        ExecuteGit executeGit = new ExecuteGit("D:\\Code\\JavaProjectList\\AlgorithmTraining\\");
        executeGit.addGitCmd("git blame -L 1,1 .\\src\\main\\java\\org\\algorithm\\training\\Advanced\\RMQ_LikeAlgorithm\\LCA.java");
        executeGit.addGitCmd("git blame -L 6,6 .\\src\\main\\java\\org\\algorithm\\training\\Advanced\\RMQ_LikeAlgorithm\\ST.java");
        executeGit.executeGitCmdExecuteList();
        for (String result : executeGit.getGitCmdResultList()) {
            System.out.println(result);
        }
    }
}
