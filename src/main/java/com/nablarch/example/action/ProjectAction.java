package com.nablarch.example.action;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.nablarch.example.form.ProjectUpdateForm;
import nablarch.common.dao.UniversalDao;
import nablarch.core.beans.BeanUtil;
import nablarch.core.validation.ee.ValidatorUtil;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

import com.nablarch.example.dto.ProjectSearchDto;
import com.nablarch.example.entity.Project;
import com.nablarch.example.form.ProjectForm;
import com.nablarch.example.form.ProjectSearchForm;

import java.util.List;

/**
 * プロジェクト検索・登録・更新機能。
 *
 * @author Nabu Rakutaro
 */
public class ProjectAction {

    /**
     * プロジェクト情報を検索する。
     *
     * @param req HTTPリクエスト
     * @return プロジェクト情報リスト
     */
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> find(HttpRequest req) {

        // リクエストパラメータをBeanに変換
        ProjectSearchForm form = BeanUtil.createAndCopy(ProjectSearchForm.class, req.getParamMap());

        // BeanValidation実行
        ValidatorUtil.validate(form);

        ProjectSearchDto searchCondition = BeanUtil.createAndCopy(ProjectSearchDto.class, form);
        return UniversalDao.findAllBySqlFile(Project.class, "FIND_PROJECT", searchCondition);
    }

    /**
     * プロジェクト情報を登録する。
     *
     * @param project プロジェクト情報
     * @return HTTPレスポンス
     */
    @Consumes(MediaType.APPLICATION_JSON)
    @Valid
    public HttpResponse save(ProjectForm project) {
        UniversalDao.insert(BeanUtil.createAndCopy(Project.class, project));
        return new HttpResponse(HttpResponse.Status.CREATED.getStatusCode());
    }

    /**
     * プロジェクト情報を更新する。
     *
     * @param form プロジェクト情報
     * @return HTTPレスポンス
     */
    @Consumes(MediaType.APPLICATION_JSON)
    @Valid
    public HttpResponse update(ProjectUpdateForm form) {
        Project project = BeanUtil.createAndCopy(Project.class, form);

        UniversalDao.update(project);

        return new HttpResponse(HttpResponse.Status.OK.getStatusCode());
    }
}
