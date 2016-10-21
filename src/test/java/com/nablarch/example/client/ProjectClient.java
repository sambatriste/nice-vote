package com.nablarch.example.client;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.nablarch.example.form.ProjectForm;
import com.nablarch.example.form.ProjectUpdateForm;

import com.nablarch.example.entity.Project;

public class ProjectClient {

    private static final String targetUrl = "http://localhost:9080/projects";

    /**
     * プロジェクト情報の文字列変換を行う。
     * @param args 引数
     */
    public static void main(String[] args) throws Exception {

        // 検索
        // 全件検索
        System.out.print(makeDataString(getProjects()));
        // 指定条件検索
        String searchParam = "?clientId=1";
        System.out.print(makeDataString(getProjects(searchParam)));

        // 登録
        ProjectForm project = createInsertProject();
        System.out.println("insert status:" + postProject(project));
        System.out.print(makeDataString(getProjects()));

        // 更新対象プロジェクト取得
        String updateSearchParam = "?projectName=プロジェクト９９９";
        Project updateProject = getProjects(updateSearchParam).get(0);
        ProjectUpdateForm updateForm = setUpdateProject(updateProject);

        // 更新
        System.out.println("update status:" + putProject(updateForm, updateProject.getProjectId()));
        System.out.print(makeDataString(getProjects()));
    }

    /**
     * 登録用プロジェクト情報生成
     * @return 登録用プロジェクト情報
     */
    private static ProjectForm createInsertProject() {
        ProjectForm form = new ProjectForm();
        form.setProjectName("プロジェクト９９９");
        form.setProjectType("development");
        form.setProjectClass("s");
        form.setProjectStartDate("20160101");
        form.setProjectEndDate("20160331");
        form.setClientId("10");
        form.setProjectManager("鈴木");
        form.setProjectLeader("佐藤");
        form.setNote("備考９９９");
        form.setSales("10000");
        form.setCostOfGoodsSold("1000");
        form.setSga("2000");
        form.setAllocationOfCorpExpenses("3000");
        return form;
    }

    /**
     * 更新用プロジェクト情報設定
     * @param project 更新対象プロジェクト情報
     * @return 更新用プロジェクト情報
     */
    private static ProjectUpdateForm setUpdateProject(Project project) {
        ProjectUpdateForm form = new ProjectUpdateForm();
        form.setProjectId(project.getProjectId().toString());
        form.setProjectName("プロジェクト８８８");
        form.setProjectType("development");
        form.setProjectClass("a");
        form.setProjectStartDate("20150101");
        form.setProjectEndDate("20150331");
        form.setClientId("1");
        form.setProjectManager("佐藤");
        form.setProjectLeader("鈴木");
        form.setNote("備考８８８");
        form.setSales("20000");
        form.setCostOfGoodsSold("2000");
        form.setSga("3000");
        form.setAllocationOfCorpExpenses("4000");
        return form;
    }

    /**
     * HTTP GETメソッドを使用したクライアント操作を行う。
     * @return プロジェクト情報リスト
     */
    private static List<Project> getProjects() {
        return ClientBuilder.newClient()
                .target(targetUrl)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Project>>() {});
    }

    /**
     * HTTP GETメソッドを使用したクライアント操作を行う。
     * @param param 検索条件パラメータ
     * @return プロジェクト情報リスト
     */
    private static List<Project> getProjects(String param) throws UnsupportedEncodingException {

        return ClientBuilder.newClient()
                .target(targetUrl + param)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Project>>() {});
    }

    /**
     * HTTP POSTメソッドを使用したクライアント操作を行う。
     * @param project 更新用プロジェクト情報
     * @return ステータスコード
     */
    private static Integer postProject(ProjectForm project) {
        return ClientBuilder.newClient()
                .target(targetUrl)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(project)).getStatus();
    }

    /**
     * HTTP PUTメソッドを使用したクライアント操作を行う。
     * @param project 更新用プロジェクト情報
     * @param projectId プロジェクトID
     * @return ステータスコード
     */
    private static Integer putProject(ProjectUpdateForm project, Integer projectId) {
        return ClientBuilder.newClient()
                .target(targetUrl)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(project)).getStatus();
    }

    /**
     * プロジェクト情報の文字列変換を行う。
     * @param projects プロジェクト情報List
     * @return プロジェクト情報
     */
    private static String makeDataString(List<Project> projects) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("---- projects (size: %s) ----", projects.size())).append('\n');
        for (Project project : projects) {
            sb.append(String.format("Project(ProjectId: %s, ProjectName: %s, ProjectType: %s, ProjectClass: %s, "
                    + "ProjectStartDate: %s, ProjectEndDate: %s, ClientId: %s, ProjectManager: %s, ProjectLeader: %s, "
                    + "UserId: %s, Note: %s, Sales: %s, CostOfGoodsSold: %s, Sga: %s, AllocationOfCorpExpenses: %s, "
                    + "Client: %s, SystemAccount: %s)",
                    project.getProjectId(), project.getProjectName(), project.getProjectType(),
                    project.getProjectClass(), project.getProjectStartDate(), project.getProjectEndDate(),
                    project.getClientId(), project.getProjectManager(), project.getProjectLeader(),
                    project.getUserId(), project.getNote(), project.getSales(), project.getCostOfGoodsSold(),
                    project.getSga(), project.getAllocationOfCorpExpenses(), project.getClient(),
                    project.getSystemAccount())).append('\n');
        }
        return sb.toString();
    }
}
