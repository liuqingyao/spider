package com.spider.service;

import com.spider.bean.Article;
import com.spider.bean.ArticleVO;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: daluwang
 * Date: 12-11-9
 * Time: 下午12:08
 * To change this template use File | Settings | File Templates.
 */
public class ArticleServiceImpl {

    private static ArticleServiceImpl instance = new ArticleServiceImpl();

    private ArticleServiceImpl() {

    }

    public static ArticleServiceImpl getInstance() {
        return instance;
    }

    private TTransport transport;
    private ArticleServiceImpl client;



    @PreDestroy
    public void close() {
        this.client = null;
        if (this.transport != null) {
            this.transport.close();
            this.transport = null;
        }
    }


    public long create(Article article) throws TException {

        return client.create(article);
    }


    public Article get(long l) throws TException {
        return client.get(l);
    }


    public boolean drop(List<Long> longs) throws TException {
        return client.drop(longs);
    }


    public boolean update(Article article) throws TException {
        return client.update(article);
    }


    public boolean exist(String s) throws TException {
        return client.exist(s);
    }


    public List<ArticleVO> getSpringBreezeRecommendArticles(int limit) throws TException {
        return client.getSpringBreezeRecommendArticles(limit);
    }


    public List<ArticleVO> getSpringBreezeRecommendArticlesAfterArticleId(int limit, long articleId) throws TException {
        return client.getSpringBreezeRecommendArticlesAfterArticleId(limit, articleId);
    }


    public List<ArticleVO> getSpringBreezeRecommendArticlesBeforeArticleId(int limit, long articleId) throws TException {
        return client.getSpringBreezeRecommendArticlesBeforeArticleId(limit, articleId);
    }


    public List<Long> getChannelRecommendArticles(long channelId, int limit) throws TException {
        return client.getChannelRecommendArticles(channelId, limit);
    }


    public List<Long> getChannelRecommendArticlesBeforeArticleId(long channelId, int limit, long articleId) throws TException {
        return client.getChannelRecommendArticlesBeforeArticleId(channelId, limit, articleId);
    }


    public List<Long> getChannelRecommendArticlesAfterArticleId(long channelId, int limit, long articleId) throws TException {
        return client.getChannelRecommendArticlesAfterArticleId(channelId, limit, articleId);
    }


    public List<ArticleVO> getSourcesArticles(int number, List<Long> sources) throws TException {
        return client.getSourcesArticles(number, sources);
    }


    public List<ArticleVO> getSourcesArticlesBeforeArticleId(int number, List<Long> sources, long articleId) throws TException {
        return client.getSourcesArticlesBeforeArticleId(number, sources, articleId);
    }


    public List<ArticleVO> getSourcesArticlesAfterArticleId(int number, List<Long> sources, long articleId) throws TException {
        return client.getSourcesArticlesAfterArticleId(number, sources, articleId);
    }


    public List<ArticleVO> getChannelRecommendAndSourcesArticles(int limit, long channelId, List<Long> sources) throws TException {
        return client.getChannelRecommendAndSourcesArticles(limit, channelId, sources);
    }


    public List<ArticleVO> getChannelRecommendAndSourcesArticlesBeforeArticleId(int limit, long channelId, List<Long> sources, long articleId) throws TException {
        return client.getChannelRecommendAndSourcesArticlesBeforeArticleId(limit, channelId, sources, articleId);
    }


    public List<ArticleVO> getChannelRecommendAndSourcesArticlesAfterArticleId(int limit, long channelId, List<Long> sources, long articleId) throws TException {
        return client.getChannelRecommendAndSourcesArticlesAfterArticleId(limit, channelId, sources, articleId);
    }


    public List<ArticleVO> getArticles(List<Long> articleIds) throws TException {
        return client.getArticles(articleIds);
    }


    public Article getArticleByUrl(String s) throws TException {
        return client.getArticleByUrl(s);  //To change body of implemented methods use File | Settings | File Templates.
    }


    public ArticleVO getArticleVO(long id) throws TException {
        return client.getArticleVO(id);
    }


    public long createArticleVO(ArticleVO articleVO) throws TException {
        return client.createArticleVO(articleVO);
    }


    public boolean dropArticleVO(long id) throws TException {
        return false;
    }


    public boolean updateArticleVO(ArticleVO articleVO) throws TException {
        return false;
    }


    public List<ArticleVO> getPageSearchArticles(int i, long l, int i1, long l1, long l2, int i2, int i3, long l3, int i4, int i5, String s, long l4, int i6, int i7, int i8) throws TException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public int getPageSearchArticlesCount(int i, long l, int i1, long l1, long l2, int i2, int i3, long l3, int i4, int i5, String s, long l4, int i6, int i7, int i8) throws TException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public List<ArticleVO> getPageSearchArticles(int i, long l, int i1, long l1, long l2, int i2, int i3, long l3, int i4, int i5, String s, long l4, int i6, int i7) throws TException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public int getPageSearchArticlesCount(int i, long l, int i1, long l1, long l2, int i2, int i3, long l3, int i4, int i5, String s, long l4, int i6, int i7) throws TException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<ArticleVO> getPageSearchArticles(int i, long l, int i1, long l1, long l2, int i2, int i3, long l3, int i4, int i5, String s, long l4, int i6) throws TException {
        return null;
    }

    public int getPageSearchArticlesCount(int i, long l, int i1, long l1, long l2, int i2, int i3, long l3, int i4, int i5, String s, long l4, int i6) throws TException {
        return 0;
    }


    public List<ArticleVO> getArticleBySourceId(long sourceId) throws TException {
        return null;
    }


    public List<ArticleVO> getArticleByRecommendFlag(int recommendFlag) throws TException {
        return null;
    }


    public List<ArticleVO> getArticleByChannelRecommend(long channelId, int channelRecommend) throws TException {
        return null;
    }


    public int updateRecommendFlag(long l, int i, int i1) throws TException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public int updateChannelRecommend(long l, long l1, int i) throws TException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int updateRecommendFlag(long l, int i) throws TException {
        return 0;
    }


    public int updateChannelRecommend(long articleId, long channelId) throws TException {
        return 0;
    }


    public int cancelRecommend(long articleId) throws TException {
        return 0;
    }


    public int updateArticleCommentFlag(long articleId, int commentFlag) throws TException {
        return 0;
    }


    public int deleteFromChannel(long articleId) throws TException {
        return 0;
    }


    public int updateTitleAndContext(long articleId, String title, String content) throws TException {
        return 0;
    }


    public boolean updateArticleBySimilar(long l, long l1) throws TException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
