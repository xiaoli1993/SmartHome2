/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.baselibrary.manage;

import com.heiman.baselibrary.mode.Link;
import com.heiman.baselibrary.mode.link.PL;
import com.heiman.baselibrary.mode.link.plBean.LinkBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.CheckListBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.ConListBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.ExecBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.checkListBean.TimeBeanX;
import com.heiman.baselibrary.mode.link.plBean.linkBean.conListBean.TimeBean;
import com.heiman.baselibrary.mode.scene.pLBean.SceneBean;
import com.heiman.utils.LogUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/12 10:22
 * @Description : 
 * @Modify record :
 */

public class LinkManage {
    private static LinkManage instance;
    private List<Link> linkList = new ArrayList<>();

    private LinkManage(){

    }

    public static LinkManage getInstance() {
        if (instance == null) {
            instance = new LinkManage();
        }
        return instance;
    }

    public List<Link> getLinkList() {
        List<Link> results = new ArrayList<>();
        results.addAll(linkList);
        return results;
    }

    /**
     * 查找数据库中所有联动
     * @return
     */
    public List<Link> findAllLink() {
        linkList.clear();
        linkList.addAll(DataSupport.findAll(Link.class));

        if (linkList != null && linkList.size() > 0) {

            for (int i = 0; i < linkList.size(); i++) {
                List<PL> plList = DataSupport.where("link_id = ?", linkList.get(i).getId() + "").find(PL.class);

                if (plList != null && plList.size() > 0) {
                    linkList.get(i).setPL(plList.get(0));
                    List<LinkBean> linkBeanList = DataSupport.where("pl_id = ?", plList.get(0).getId() + "").find(LinkBean.class);

                    if (linkBeanList != null && linkBeanList.size() > 0) {
                        linkList.get(i).getPL().setOID(linkBeanList.get(0));
                        List<ExecBean> execBeanList = DataSupport.where("linkbean_id = ?", linkBeanList.get(0).getId() + "").find(ExecBean.class);

                        if (execBeanList != null) {
                            linkList.get(i).getPL().getOID().setExecList(execBeanList);
                        }

                        List<ConListBean> conListBeanList = DataSupport.where("linkbean_id = ?", linkBeanList.get(0).getId() + "").find(ConListBean.class);

                        if (conListBeanList != null && conListBeanList.size() > 0) {
                            linkList.get(i).getPL().getOID().setConList(conListBeanList.get(0));
                            List<TimeBean> timeBeanList = DataSupport.where("conlistbean_id = ?", conListBeanList.get(0).getId() + "").find(TimeBean.class);

                            if (timeBeanList != null && timeBeanList.size() > 0) {
                                linkList.get(i).getPL().getOID().getConList().setTime(timeBeanList.get(0));
                            }
                        }

                        List<CheckListBean> checkListBeanList = DataSupport.where("linkbean_id = ?", linkBeanList.get(0).getId() + "").find(CheckListBean.class);

                        if (checkListBeanList != null) {
                            linkList.get(i).getPL().getOID().setCheckList(checkListBeanList);

                            for (int j = 0; j < checkListBeanList.size(); j++) {
                                List<TimeBeanX> timeBeanXList = DataSupport.where("checklistbean_id = ?", checkListBeanList.get(j).getId() + "").find(TimeBeanX.class);

                                if (timeBeanXList != null && timeBeanXList.size() > 0) {
                                    checkListBeanList.get(j).setTime(timeBeanXList.get(0));
                                }
                            }
                        }
                    }
                }
            }
        }
        return getLinkList();
    }

    /**
     * 根据主键id查找到联动link
     * @param id
     * @return
     */
    public Link getLink(long id) {
        Link link = DataSupport.find(Link.class, id);
        if (link != null) {
            List<PL> plList = DataSupport.where("link_id = ?", link.getId() + "").find(PL.class);

            if (plList != null && plList.size() > 0) {
                link.setPL(plList.get(0));
                List<LinkBean> linkBeanList = DataSupport.where("pl_id = ?", plList.get(0).getId() + "").find(LinkBean.class);

                if (linkBeanList != null && linkBeanList.size() > 0) {
                    link.getPL().setOID(linkBeanList.get(0));
                    List<ExecBean> execBeanList = DataSupport.where("linkbean_id = ?", linkBeanList.get(0).getId() + "").find(ExecBean.class);

                    if (execBeanList != null) {
                        link.getPL().getOID().setExecList(execBeanList);
                    }

                    List<ConListBean> conListBeanList = DataSupport.where("linkbean_id = ?", linkBeanList.get(0).getId() + "").find(ConListBean.class);

                    if (conListBeanList != null && conListBeanList.size() > 0) {
                        link.getPL().getOID().setConList(conListBeanList.get(0));
                        List<TimeBean> timeBeanList = DataSupport.where("conlistbean_id = ?", conListBeanList.get(0).getId() + "").find(TimeBean.class);

                        if (timeBeanList != null && timeBeanList.size() > 0) {
                            link.getPL().getOID().getConList().setTime(timeBeanList.get(0));
                        }
                    }

                    List<CheckListBean> checkListBeanList = DataSupport.where("linkbean_id = ?", linkBeanList.get(0).getId() + "").find(CheckListBean.class);

                    if (checkListBeanList != null) {
                        link.getPL().getOID().setCheckList(checkListBeanList);

                        for (int j = 0; j < checkListBeanList.size(); j++) {
                            List<TimeBeanX> timeBeanXList = DataSupport.where("checklistbean_id = ?", checkListBeanList.get(j).getId() + "").find(TimeBeanX.class);

                            if (timeBeanXList != null && timeBeanXList.size() > 0) {
                                checkListBeanList.get(j).setTime(timeBeanXList.get(0));
                            }
                        }
                    }
                }
            }
        }
        return link;
    }

    /***
     * 根据联动linkId从数据库中查找Link
     * @param linkId
     * @return
     */
    public Link getLink(int linkId) {
        Link link = null;
        List<Link> links = DataSupport.where("id = (select link_id from pl where id = (select pl_id from linkbean where linkid = ?))", linkId +"").find(Link.class);
        if (links != null && links.size() > 0) {
            link = getLink(links.get(0).getId());
        }
        return link;
    }

    /**
     * 修改TimeBean数据库中有则更新无则新增
     * @param timeBean
     */
    private void modifyTimeBean(TimeBean timeBean) {
        if (timeBean == null) {
            LogUtil.e("modify timeBean is null!");
            return;
        }
        List<TimeBean> timeBeanList = null;
        try {
            timeBeanList = DataSupport.where("id = ?", timeBean.getId() + "").find(TimeBean.class);
        } catch (Exception e) {
            timeBean.save();
        }

        if (timeBeanList != null && timeBeanList.size() > 0) {
            timeBean.update(timeBean.getId());
        } else {
            timeBean.save();
        }
    }

    /**
     * 修改TimBeanx有则更新没有则新增
     * @param timeBeanX
     */
    private void modifyTimeBeanX(TimeBeanX timeBeanX) {
        if (timeBeanX == null) {
            LogUtil.e("modify timeBeanX is null!");
            return;
        }
        List<TimeBeanX> timeBeanXList = null;
        try {
            timeBeanXList = DataSupport.where("id = ?", timeBeanX.getId() + "").find(TimeBeanX.class);
        } catch (Exception e) {
            timeBeanX.save();
        }

        if (timeBeanXList != null && timeBeanXList.size() > 0) {
            timeBeanX.update(timeBeanX.getId());
        } else {
            timeBeanX.save();
        }
    }

    /**
     * 修改ExecBean有则更新没有则新增
     * @param execBean
     */
    private void modifyExecBean(ExecBean execBean) {
        if (execBean == null) {
            LogUtil.e("modify execBean is null!");
            return;
        }
        List<ExecBean> execBeanList = null;
        try {
            execBeanList = DataSupport.where("id = ?", execBean.getId() + "").find(ExecBean.class);
        } catch (Exception e) {
            execBean.save();
        }

        if (execBeanList != null && execBeanList.size() > 0) {
            execBean.update(execBean.getId());
        } else {
            execBean.save();
        }
    }

    /**
     * 修改ConListBean 数据库中有则更新没有则新增
     * @param conListBean
     */
    private void modifyConListBean(ConListBean conListBean) {
        if (conListBean == null) {
            LogUtil.e("modify conListBean is null!");
            return;
        }
        List<ConListBean> conListBeanList = null;
        try {
            conListBeanList = DataSupport.where("id = ?", conListBean.getId() + "").find(ConListBean.class);
        } catch (Exception e) {
            conListBean.save();
        }

        if (conListBeanList != null && conListBeanList.size() > 0) {
            conListBean.update(conListBean.getId());
        } else {
            conListBean.save();
        }
    }

    /**
     * 修改CheckListBean数据库中有则更新没有则新增
     * @param checkListBean
     */
    private void modifyCheckListBean(CheckListBean checkListBean) {
        if (checkListBean == null) {
            LogUtil.e("modify checkListBean is null!");
            return;
        }
        List<CheckListBean> checkListBeanList = null;
        try {
            checkListBeanList = DataSupport.where("id = ?", checkListBean.getId() + "").find(CheckListBean.class);
        } catch (Exception e) {
            checkListBean.save();
        }

        if (checkListBeanList != null && checkListBeanList.size() > 0) {
            checkListBean.update(checkListBean.getId());
        } else {
            checkListBean.save();
        }
    }

    /**
     * 修改linkBean数据库中有则更新没有则新增
     * @param linkBean
     */
    private void modifyLinkBean(LinkBean linkBean) {
        if (linkBean == null) {
            LogUtil.e("modify linkBean is null!");
            return;
        }
        List<LinkBean> linkBeanList = null;
        try {
            linkBeanList = DataSupport.where("id = ?", linkBean.getId() + "").find(LinkBean.class);
        } catch (Exception e) {
            linkBean.save();
        }

        if (linkBeanList != null && linkBeanList.size() > 0) {
            linkBean.update(linkBean.getId());
        } else {
            linkBean.save();
        }
    }

    /**
     * 修改pl数据库中有则更新没有则新增
     * @param pl
     */
    private void modifyPL(PL pl) {
        if (pl == null) {
            LogUtil.e("modify pl is null!");
            return;
        }
        List<PL> plList = null;
        try {
            plList = DataSupport.where("id = ?", pl.getId() + "").find(PL.class);
        } catch (Exception e) {
            pl.save();
        }

        if (plList != null && plList.size() > 0) {
            pl.update(pl.getId());
        } else {
            pl.save();
        }
    }

    /**
     * 修改link数据库中有则更新没有则新增
     * @param link
     */
    private void modifyLink(Link link) {
        if (link == null) {
            LogUtil.e("modify link is null!");
            return;
        }
        List<Link> linkList = null;
        try {
            linkList = DataSupport.where("id = ?", link.getId() + "").find(Link.class);
        } catch (Exception e) {
            link.save();
        }

        if (linkList != null && linkList.size() > 0) {
            link.update(link.getId());
        } else {
            link.save();
        }
    }

    /**
     * 更新数据库中的联动link
     * @param link
     */
    public void updateLink(Link link) {
        if (link == null) {
            LogUtil.e("updateLink link is null!");
            return;
        }

        modifyLink(link);

        if (link.getPL() == null) {
            return;
        }

        modifyPL(link.getPL());

        if (link.getPL().getOID() == null) {
            return;
        }

        modifyLinkBean(link.getPL().getOID());

        if (link.getPL().getOID().getExecList() != null) {
            for (int i = 0; i < link.getPL().getOID().getExecList().size(); i++) {
                modifyExecBean(link.getPL().getOID().getExecList().get(i));
            }
        }

        if (link.getPL().getOID().getConList() != null) {
            modifyConListBean(link.getPL().getOID().getConList());

            if (link.getPL().getOID().getConList().getTime() != null) {
                modifyTimeBean(link.getPL().getOID().getConList().getTime());
            }
        }

        if (link.getPL().getOID().getCheckList() != null) {

            for (int i = 0; i < link.getPL().getOID().getCheckList().size(); i++) {
                CheckListBean checkListBean = link.getPL().getOID().getCheckList().get(i);
                if (checkListBean != null) {
                    modifyCheckListBean(checkListBean);

                    if (checkListBean.getTime() != null) {
                        modifyTimeBeanX(checkListBean.getTime());
                    }
                }
            }
        }
    }

    /**
     * 删除联动
     * @param link
     */
    public void deleteLink(Link link) {
        if (link == null) {
            LogUtil.e("delete link is null！");
            return;
        }

        if (link.getPL() != null && link.getPL().getOID() != null) {
            LinkBean linkBean = link.getPL().getOID();
            if (linkBean.getExecList() != null) {
                for (int i = 0; i < link.getPL().getOID().getExecList().size(); i++) {
                    ExecBean execBean = link.getPL().getOID().getExecList().get(i);

                    if (execBean != null) {
                        execBean.delete();
                    }
                }
            }

            if (linkBean.getConList() != null) {
                ConListBean conListBean = linkBean.getConList();

                if (conListBean.getTime() != null) {
                    conListBean.getTime().delete();
                }

                conListBean.delete();
            }

            if (linkBean.getCheckList() != null) {
                for (int i = 0; i < linkBean.getCheckList().size(); i++) {
                    CheckListBean checkListBean = linkBean.getCheckList().get(i);

                    if (checkListBean != null) {

                        if (checkListBean.getTime() != null) {
                            checkListBean.getTime().delete();
                        }
                        checkListBean.delete();
                    }
                }
            }
            linkBean.delete();
        }

        if (link.getPL() != null) {
            link.getPL().delete();
        }

        link.delete();
    }

    /**
     * 新增联动
     * @param link
     */
    public void addLink(Link link) {
        if (link == null) {
            LogUtil.e("add link is null!");
            return;
        }

        link.save();

        if (link.getPL() != null) {
            modifyPL(link.getPL());

            if (link.getPL().getOID() != null) {
                LinkBean linkBean = link.getPL().getOID();
                modifyLinkBean(linkBean);

                if (linkBean.getExecList() != null) {
                    for (int i = 0; i < linkBean.getExecList().size(); i++) {
                        ExecBean execBean = linkBean.getExecList().get(i);
                        modifyExecBean(execBean);
                    }
                }

                if (linkBean.getConList() != null) {
                    modifyConListBean(linkBean.getConList());

                    if (linkBean.getConList().getTime() != null) {
                        modifyTimeBean(linkBean.getConList().getTime());
                    }
                }

                if (linkBean.getCheckList() != null) {
                    for (int i = 0; i < linkBean.getCheckList().size(); i++) {
                        CheckListBean checkListBean = linkBean.getCheckList().get(i);
                        modifyCheckListBean(checkListBean);

                        if (checkListBean.getTime() != null) {
                            modifyTimeBeanX(checkListBean.getTime());
                        }
                    }
                }
            }
        }
    }
}
