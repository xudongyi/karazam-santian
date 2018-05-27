[@nestedstyle]
    [@css href="css/build.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/login/login_register.js" /]
    [@js src="js/login/publicjs.js" /]
    [@js src="lib/zui/js/zui.js" /]
[/@nestedscript]
[@insert template="layout/mobile_regist_layout" title="注册"]
<div >
    <div class="content clear">
        <div class="register-con">
            <div class="register-top clear">
                [#--<h3 class="fsz22">注册</h3>--]
                [#--<p class="link-a pull-right">已有账户？<a href="${ctx}/login">登录</a></p>--]
            </div>
            <div class="register-form basic-form">
                <form role="form">
                    <div class="radio">
                        <div class="radio radio-inline">
                            <input type="radio" name="radio1" id="radio1" value="GENERAL" checked="">
                            <label for="radio1">
                                个人用户
                            </label>
                        </div>
                        <div class="radio radio-inline">
                            <input type="radio" name="radio1" id="radio2" value="ENTERPRISE">
                            <label for="radio2">
                                企业用户
                            </label>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <div class="formCon">
                            <input type="text" class="form-control nameIco" id="regname" maxlength="11"
                                   placeholder="请输入手机号码">
                            <span class="help-block" id="errorregname"></span>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <div class="formCon">
                            <div class="clear">
                                <input type="text" id="mcode" class="form-control tuijIco tuijw" placeholder="手机验证码">
                                <a class="xianzi pull-right" id="regyanzbtn"
                                   style=" line-height: 38px; font-size: 14px; ">获取验证码</a>
                            </div>
                            <span class="help-block" id="errorcapcha"></span>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <div class="formCon">
                            <input type="password" class="form-control passwordIco" id="regpassword" maxlength="18"
                                   placeholder="请输入登录密码（6-18位字母和数字）">
                            <span class="help-block" id="errorregpwd"></span>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <div class="formCon">
                            <input type="password" class="form-control passwordIco" id="regpassword1" maxlength="18"
                                   placeholder="请再次输入密码">
                            <span class="help-block" id="errorregpwd1"></span>
                        </div>
                    </div>
                    <div class="form-group clear disNone">
                        <label class="control-label pull-left">电子邮件：</label>
                        <div class="formCon ">
                            <input type="email" class="form-control nameIco" id="email" placeholder="可通过邮箱找回密码。">
                            <span class="help-block" id="erroremail"></span>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <div class="collapse1 form-group" id="collapseExample">
                            <div class="well"
                                 style=" width: auto; box-shadow: none;padding: 0;  margin-bottom: 0; background-color: #fff; border: none; border-radius: 0;">
                                <div class="formCon" style="width: 290px;">
                                    <div class="clear">
                                        [#if inviteCode??]
                                            <input type="text" id="recomcode" value="${referrer}" class="form-control tuijIco" placeholder="请填写推荐人（选填）">
                                            <input type="hidden" id="inviteCode" name="inviteCode" value="${inviteCode}" />
                                        [#else]
                                            <input type="text" id="recomcode" class="form-control tuijIco" placeholder="请填写推荐人（选填）">
                                        [/#if]
                                        <label class="xianzi ellipsis-single disNone" id="tjrid"></label>
                                    </div>
                                    <span class="help-block" id="errorrecomcode"></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="agreeform">
                        <div class="register-check">
                            <label class="disInb pos-re">
                                <input id="incheck" checked="checked" type="checkbox" value="1" name="checkBox"/>
                                <i class="noHad-icon"></i>
                            </label>
                            我已阅读并同意<a id="tcdiv" href="#" data-toggle="modal" data-target="#t_and_c_m">《${setting.basic.siteName}注册及服务协议》</a>
                        </div>
                        <div id="serverErrorMsg" style="margin-bottom:12px;width:290px;"></div>
                        <input type="hidden" id="checkcode1" value="0"/>
                        <a href="javascript:void(0)" class="btn btn-primary regBtna" onclick="registerbtn()">立即注册</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div aria-hidden="false" aria-labelledby="mySmallModalLabel" id="reg_VerCode" role="dialog" tabindex="-1"
         class="modal fade in" style="">
        <div class="modal-backdrop fade in" style="height: 100%;"></div>
        <div class="modal-dialog modalMw suiji_yan_tk">
            <div class="modal-content suiji_yaz ">
                <div class="modal-header">
                    <button data-dismiss="modal" class="close" type="button" onclick="hideVerCode()"><span
                            aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">随机验证码</h4></div>
                <div class="modal-body">
                    <div class="yanz_connet"><input id="veryCode1" class="form-control loginYanz" maxlength="6"
                                                    name="veryCode" type="text" placeholder="请输入图形验证码"> <span
                            class="yanz_img"><a href="javascript:changeImg(130,54)"><img id="imgObj"
                                                                                         src="https://www.33lend.com/portalLoginController.do?verify&amp;localtion=LoginYZM"
                                                                                         border="0"></a></span></div>
                    <div class="suiji_pro"><span id="error_code"></span></div>
                    <div class="yanz_btn"><a href="javascript:void(0);"
                                             onclick="checkVeryCode(&quot;portalMobileRecomService:sendMobileRecom&quot;,&quot;portalMobileRecomService:sendMobileRecom&quot;,&quot;regname&quot;,&quot;regyanzbtn&quot;,&quot;0&quot;,&quot;&quot;,&quot;LoginYZM&quot;)"
                                             class="btn btn-primary" type="button">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确&nbsp;定&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="t_and_c_m" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">${setting.basic.siteName}注册协议</h4>
                </div>
                <div class="modal-body" style="height:500px; overflow-y:scroll;">
                    <p>
                        本网站由青海高领网络科技有限公司（下称“${setting.basic.siteName}”）所有和负责运营。本使用协议双方为${setting.basic.siteName}与本网站注册用户（下称“用户”或“您”），本协议具有合同效力，适用于您在本网站的全部活动。请您在注册成为本网站用户前务必仔细阅读本协议，若您不同意本协议的任何内容，或者无法准确理解${setting.basic.siteName}对条款的解释，请不要进行后续操作；若您注册成为本网站用户，则表示您对本协议的全部内容已充分阅读并认可和同意遵守。同时，承诺遵守中国法律、法规、规章及其他政府规范性文件规定，如有违反而造成任何法律后果，您将以本人名义独立承担所有相应的法律责任。</p>
                    <p>本协议内容包括以下条款及本网站已经发布的或将来可能发布的各类规则。本网站发布的所有规则为本协议不可分割的一部分，与协议正文具有同等法律效力。</p>
                    <p>
                        ${setting.basic.siteName}有权根据需要不定时地制定、修改本协议或各类规则，对于本网站用户适用的协议或各类规则应以用户在本网站注册时适用的协议或规则内容为准。本网站将随时刊载并公告本协议及规则变更情况。经修订的协议、规则一经公布，立即自动生效，对新协议、规则生效之后注册的用户发生法律效力。对于协议、规则生效之前注册的用户，若用户在新规则生效后继续使用本网站提供的各项服务，则表明用户已充分阅读并认可和同意遵守新的协议或规则。若用户拒绝接受新的协议和规则，用户有权放弃或终止继续使用本网站提供的各项服务，但该用户应承担在本网站已经进行的交易下所应承担的任何法律责任，且应遵循该用户发生交易时有效的协议或规则内容。</p>
                    <h5 align="center">一、 注册规则</h5>
                    <h5>1、注册与用户定义</h5>
                    <p>
                        用户注册是指欲申请成为本网站用户的人员登录，并按要求填写相关信息并确认同意履行相关用户协议的过程。个人用户是指通过本网站完成全部注册程序后，使用本网站服务，并且符合中华人民共和国法律规定的具有完全民事行为能力（18周岁以上的自然人），能够独立承担民事责任的自然人。机构用户是指通过本网站完成全部注册程序后，使用本网站服务，并且依据中华人民共和国法律在中华人民共和国境内（香港、台湾、澳门除外）设立的，具有民事行为能力，能够独立承担民事责任的法人或其他组织。</p>
                    <h5>2、注册资料要求</h5>
                    <p>申请成为本网站用户，首先需要完成注册。在注册时，您将选择用户名和密码，用户名的选择应遵守法律法规及社会公德。</p>
                    <p>同时，您必须按照申请注册用户的表格填写您的资料， 并遵守如下要求：</p>
                    <p>（1）您必须提供真实、最新、有效及完整的资料，并且授予${setting.basic.siteName}基于提供网站服务的目的，对您提供的资料及数据信息拥有全球通用的、永久的、免费的使用权利。</p>
                    <p>
                        （2）您有义务维持并更新注册的用户资料，确保其为真实、最新、有效及完整的资料。如因您未及时更新基本资料，导致${setting.basic.siteName}服务无法提供或提供时发生任何错误，您不得将此作为取消交易或拒绝付款的理由，${setting.basic.siteName}亦不承担任何责任，所有后果应由您承担。</p>
                    <p>
                        （3）若您提供任何错误、虚假、过时或不完整的资料，或者${setting.basic.siteName}依其独立判断怀疑资料为错误、虚假、过时或不完整的，${setting.basic.siteName}有权暂停或终止您在本网站的注册账号，并拒绝您使用本网站服务的部分或全部功能。在此情况下，${setting.basic.siteName}不承担任何责任，您同意承担因此所产生的直接或间接的任何支出或损失。</p>
                    <h5>3、用户名及登录密码的使用</h5>
                    <p>
                        您拥有在本网站的用户名及登录密码，并有权使用自己的用户名及密码随时登录本网站，您不得以任何形式擅自转让或授权他人使用自己的用户名及密码。${setting.basic.siteName}通过用户的注册用户名及密码来识别用户的指令。用户确认，使用用户名和密码登录后在本网站的一切行为均代表用户本人。用户注册操作所产生的电子信息记录均为用户行为的有效凭证，并由用户本人承担由此产生的全部责任。</p>
                    <h5 align="center">二、用户的义务</h5>
                    <h5>1、保证资金来源合法</h5>
                    <p>用户保证并承诺通过本网站进行交易的资金来源合法。</p>
                    <h5>2、信息发布真实有效</h5>
                    <p>用户承诺，其通过本网站发布的信息均真实有效，其向${setting.basic.siteName}提交的任何资料均真实、有效、完整、准确。如因违背上述承诺，造成${setting.basic.siteName}或本网站其他用户损失的，用户将承担相应责任。</p>
                    <h5>3、禁止对网站数据进行商业性利用</h5>
                    <p>用户承诺，不对本网站上的任何数据作商业性利用，包括但不限于在未经${setting.basic.siteName}事先书面同意的情况下，以复制、传播等任何方式使用本站上展示的资料。</p>
                    <h5>4、遵守法律法规</h5>
                    <p>用户在接受${setting.basic.siteName}服务时必须遵守中国法律、法规、规章以及政府规范性文件，不得做出违法违规的行为，具体如下：</p>
                    <p>（1）发表、传送、传播、储存侵害他人知识产权、商业秘密等合法权利的内容。</p>
                    <p>（2）制造虚假身份、发布虚假信息等误导、欺骗他人，或违背${setting.basic.siteName}页面公布之活动规则进行虚假交易。</p>
                    <p>
                        （3）进行危害计算机网络安全的行为。包括但不限于在您所发布的信息中含有蓄意毁坏、恶意干扰、秘密地截取或侵占任何系统、数据或个人资料的任何病毒、伪装破坏程序、电脑蠕虫、定时程序炸弹或其他电脑程序。</p>
                    <p>（4）发布国家禁止发布的信息，发布其它涉嫌违法或违反本协议及各类规则的信息。</p>
                    <h5 align="center">三、 通知及送达</h5>
                    <h5>1、通知方式</h5>
                    <p>
                        本协议条款及任何其他的协议、告示或其他关于您使用本服务账号及服务的通知，您同意本网站使用电子方式或通过向您在注册时预留的通讯地址邮寄文件资料的方式通知您。电子方式包括但不限于以电子邮件方式、或于本网站或者合作网站上公布、或无线通讯装置通知等方式。</p>
                    <h5>2、送达</h5>
                    <p>
                        网站的通知如以公示方式做出，一经在本网站公示即视为已经送达。除此之外，其他向您个人发布的具有专属性的通知将由本网站在您注册时或者注册后变更用户信息时向本网站提供的电子邮箱、通讯地址或用户注册后在本网站绑定的手机号码发送，一经发送即视为已经送达。请您密切关注用户的电子邮箱、注意接收邮寄至通讯地址的邮件以及手机中的短信信息。因信息传输等原因导致您未在前述通知发出当日收到该等通知的，本网站不承担责任。</p>
                    <h5>3、通知内容</h5>
                    <p>
                        您同意${setting.basic.siteName}利用在本网站登记的联系方式与您联络并向您传递相关的信息，包括但不限于行政管理方面的通知、产品信息、合同履行过程中的通知、债权转让通知、有关您使用本网站的通讯以及针对性的广告条等。</p>
                    <h5 align="center">四、 服务提供与服务费用</h5>
                    <h5>1、服务内容</h5>
                    <p>本网站服务内容主要包括发布借款需求、查阅交易机会、签订和查阅合同、资金充值、提现等，具体详情以${setting.basic.siteName}当时公布的服务内容为准。</p>
                    <h5>2、服务费用</h5>
                    <p>
                        当用户使用本网站服务时，${setting.basic.siteName}将向用户收取相应服务费用。各项服务具体费用标准请详见网站上公布的业务定价及有关规则。用户承诺按照${setting.basic.siteName}公布的服务费用标准向${setting.basic.siteName}支付服务费用。用户通过本网站与其他方签订协议的，用户按照签署的协议约定向其他方支付费用。</p>
                    <p>${setting.basic.siteName}保留单方面制定与调整服务费用的权利。费用标准一旦被修改，即公布在本网站上，修改后的费用标准一经公布即行生效，适用于费用标准修改后发生的交易。</p>
                    <p>请注意：${setting.basic.siteName}在避免用户损失或给予用户补偿的前提下，有权单方决定暂时或永久地改变或停止提供某些服务。</p>
                    <h5 align="center">五、 隐私条款</h5>
                    <h5>1、账号及信息的安全</h5>
                    <p>
                        ${setting.basic.siteName}将竭尽所能保护所有用户信息安全，不会向任何无关第三方提供、出售、分享和注册用户的个人账号及信息。用户应保管好用户名与密码，不得向其他任何人泄露用户的用户名和密码，亦不可使用其他任何人的用户名和密码。因黑客、病毒或用户的保管疏忽等非${setting.basic.siteName}原因导致账号遭他人非法使用的，${setting.basic.siteName}不承担任何责任。</p>
                    <p>冒用他人账号的，${setting.basic.siteName}及其合法授权主体保留追究实际使用人责任的权利。</p>
                    <p>
                        ${setting.basic.siteName}将严格采取相应技术措施保护用户的账号安全，包括但不限于对用户密码加密的方式。同时，${setting.basic.siteName}将运用相关安全技术确保其掌握的信息不丢失、不被滥用和变造。但请注意，尽管${setting.basic.siteName}将竭尽全力保护用户的账号及信息安全，但在互联网情况下不存在绝对安全的保障措施。</p>
                    <h5>2、信息资料的来源</h5>
                    <p>（1）${setting.basic.siteName}收集用户的任何资料旨在保障用户顺利、有效、安全地进行交易等行为。</p>
                    <p>（2）除用户向${setting.basic.siteName}自愿提供的资料外，用户同意${setting.basic.siteName}以下列方式收集并核对用户的信息：</p>
                    <p class="p" style="text-indent:4em;">（a）通过公开及私人资料来源收集用户的额外资料。</p>
                    <p class="p" style="text-indent:4em;">
                        （b）${setting.basic.siteName}根据用户在本网站上的行为自动追踪关于用户的相关资料。${setting.basic.siteName}将合理利用该相关资料进行本网站用户的人数统计及行为的内部研究，以更好地了解您以及本网站的其他用户，从而提供更好、更完善的服务。</p>
                    <p class="p" style="text-indent:4em;">
                        （c）若其他用户或第三方向${setting.basic.siteName}发出关于用户在本网站上活动或登录事项的信息，${setting.basic.siteName}可以将这些资料收集、整理在用户的专门档案中。</p>
                    <h5>3、信息披露和使用</h5>
                    <p>在如下情况发生时，用户同意${setting.basic.siteName}披露或使用用户的个人信息：</p>
                    <p>（1）用户同意${setting.basic.siteName}利用用户的联系方式与用户联络并向用户传递与用户相关的信息，包括但不限于行政管理方面的通知、产品提供、有关用户使用${setting.basic.siteName}的服务、通讯以及针对性的广告条；</p>
                    <p>
                        （2）为了分析本网站的使用率、改善本网站的内容和产品推广形式，并使本网站的内容、设计和服务更能符合用户的要求，用户同意${setting.basic.siteName}使用用户的个人信息。${setting.basic.siteName}对个人信息的合理使用能使用户在使用本网站服务时得到更为顺利、有效、安全的体验；</p>
                    <p>（3）${setting.basic.siteName}有义务根据有关法律要求向司法机关和政府部门提供用户的个人信息；</p>
                    <p>（4）为了保护用户的合法权益或为防止严重侵害他人合法权益或为了公共利益的需要，${setting.basic.siteName}将善意地披露用户的个人信息；</p>
                    <p>（5）
                        经国家生效法律文书或行政处罚决定确认用户存在违法行为，或者${setting.basic.siteName}有足够事实依据可以认定用户存在违法或违反本注册协议的行为或者借款逾期未还的，该用户同意并授权${setting.basic.siteName}在本网站及互联网络上公布您的违法、违约行为，并将该内容记入任何与您相关的信用资料和档案；</p>
                    <p>（6）用户无权要求${setting.basic.siteName}提供其他用户的个人信息，除非司法机关或政府部门根据法律法规要求${setting.basic.siteName}提供。</p>
                    <h5>4、保密义务</h5>
                    <p>
                        ${setting.basic.siteName}及本网站所有用户应当履行保密义务。对在文件签署过程中所获取的对方的商业秘密以及相关权益（包括但不限于客户资料、策划方案、产品知识产权、协议合同、技术文档、账号密码），非经相关权利人许可，不得向第三方披露。关于被授权使用的有关资料、商业秘密等信息，只能在授权指定的范围使用，不得用于其他目的。</p>
                    <h5 align="center">六、涉及第三方网站</h5>
                    <p>1、本网站内容可能涉及由第三方所有、控制或运营的其它网站（以下称“第三方网站”），含有访问第三方网站的链接；同时，${setting.basic.siteName}将会在任何需要的时候增加商业伙伴或共用品牌的网站。</p>
                    <p>2、${setting.basic.siteName}对所有第三方网站的隐私保护措施不负任何责任。</p>
                    <p>
                        3、${setting.basic.siteName}不保证第三方网站上信息的真实性和有效性。您应按照第三方网站的相关协议与规则使用第三方网站，而不是按照本协议。第三方网站的内容、产品、广告和其他任何信息均由您自行判断并承担风险，而与${setting.basic.siteName}无关。</p>
                    <h5 align="center">七、知识产权</h5>
                    <h5>1、知识产权之内容</h5>
                    <p>
                        无论是否明示，${setting.basic.siteName}对本网站内所有非公有领域或非他方专有的信息内容享有知识产权（包括但不限于商标权、专利权、著作权、商业秘密等），信息内容包括但不限于文字、图片、软件、音频、视频、数据、源代码、设计、专有技术等。</p>
                    <h5>2、侵犯之禁止</h5>
                    <p>
                        非经${setting.basic.siteName}授权同意及法律规定，任何其他组织或个人都不得使用属于${setting.basic.siteName}的信息内容。本网站所有的产品、技术及程序等信息内容均由${setting.basic.siteName}享有知识产权或被授权使用；未经${setting.basic.siteName}授权许可，任何其他组织或个人不得擅自使用（包括但不限于以非法的方式打印、复制、传播、展示、下载等）。否则，${setting.basic.siteName}将依法追究其法律责任。</p>
                    <h5 align="center">八、违约责任</h5>
                    <p>1、当用户的行为涉嫌违反法律法规或违反本协议和 /
                        或规则的，${setting.basic.siteName}有权采取相应措施，包括但不限于直接屏蔽、删除侵权信息，或直接停止提供服务。如使${setting.basic.siteName}遭受任何损失的（包括但不限于受到第三方的索赔、受到行政管理部门的处罚等），用户还应当赔偿或补偿${setting.basic.siteName}遭受的损失及（或）发生的费用，包括诉讼费、律师费、保全费等。</p>
                    <p>
                        2、由于违反本协议，或违反其在本网站上签订的协议或文件，或由于用户使用本网站服务违反了任何法律或侵害第三方权利而导致任何第三方向${setting.basic.siteName}提出任何补偿申请或要求（包括律师费用），用户应对${setting.basic.siteName}给予全额补偿并使之不受损害。</p>
                    <p>3、 如因${setting.basic.siteName}违反有关法律、法规或本协议项下的任何条款而给用户造成损失，${setting.basic.siteName}同意承担由此造成的损害赔偿责任</p>
                    <h5 align="center">九、免责声明</h5>
                    <h5>1、风险免责</h5>
                    <p>（1）用户知晓并同意，任何通过本网站进行的交易并不能避免以下风险的产生，${setting.basic.siteName}不能也没有义务为如下风险负责：</p>
                    <p class="p" style="text-indent:4em;">（a）宏观经济风险：因宏观经济形势变化，可能引起价格等方面的异常波动，用户有可能遭受损失。</p>
                    <p class="p" style="text-indent:4em;">
                        （b）政策风险：有关法律、法规及相关政策、规则发生变化，可能引起价格等方面异常波动，用户有可能遭受损失。</p>
                    <p class="p" style="text-indent:4em;">（c）违约风险：因其他交易方无力或无意愿按时足额履约，用户有可能遭受损失。</p>
                    <p class="p" style="text-indent:4em;">（d）利率风险：市场利率变化可能对投资或持有产品的实际收益产生影响。</p>
                    <p>（2）${setting.basic.siteName}不对任何用户及 /
                        或任何交易提供任何明示或默示的担保。交易平台向用户提供的各种信息及资料仅为参考，用户应依其独立判断做出决策。用户据此进行交易的，产生的风险由用户自行承担，用户无权据此向${setting.basic.siteName}提出任何法律主张。在交易过程中，交易各方发生的纠纷，由纠纷各方自行解决，${setting.basic.siteName}作为交易平台方不承担任何责任。</p>
                    <p>（3）以上并不能揭示用户通过${setting.basic.siteName}进行交易的全部风险及市场的全部情形。用户在做出交易决策前，应全面了解相关交易，谨慎决策，并自行承担全部风险。</p>
                    <h5>2、互联网自身不稳定性免责</h5>
                    <p>
                        由于互联网本身所具有的不稳定性，${setting.basic.siteName}无法保证服务不会中断。系统因有关状况无法正常运作，使用户无法使用任何本网站服务或使用本网站服务受到任何影响时，${setting.basic.siteName}对用户或第三方不负任何责任，前述状况包括但不限于：</p>
                    <p>（1）本网站系统停机维护期间。</p>
                    <p>（2）电信设备出现故障不能进行数据传输的。</p>
                    <p>（3）由于黑客攻击、网络供应商技术调整或故障、网站升级、银行方面的问题等原因而造成的本网站服务中断或延迟。</p>
                    <h5>3、不可抗力免责</h5>
                    <p>
                        对于因${setting.basic.siteName}合理控制范围以外的原因，包括但不限于自然灾害（台风、地震、海啸、洪水）、罢工或骚乱、物质短缺或定量配给、暴动、战争行为、政府行为、通讯或其他设施故障或严重伤亡事故等，致使${setting.basic.siteName}延迟或未能履约的，${setting.basic.siteName}不对用户承担任何责任。</p>
                    <h5>4、用户自身过错免责</h5>
                    <p>
                        因用户的过错导致的任何损失，该过错包括但不限于：决策失误、操作不当、遗忘或泄露密码、密码被他人破解、用户使用的计算机系统被第三方侵入、用户委托他人代理交易时他人恶意或不当操作而造成的损失。</p>
                    <h5 align="center">十、争议解决适用法律及管辖</h5>
                    <p>1、
                        本协议的订立、变更、执行和解释，以及与本协议有关的争议解决，均应适用中华人民共和国法律。如与本协议有关的某一特定事项没有法律规定或规定不明确，则应参照通用的国际商业惯例和行业惯例。</p>
                    <p>2、 如因本协议或因${setting.basic.siteName}服务所引起或与其有关的任何争议，如协商不成，应选择如下方式解决：</p>
                    <p>（1）提交仲裁委员会按其仲裁规则仲裁，仲裁裁决是终局的，对双方均有约束力。本合同项下任何仲裁中的守约方有权要求对方支付其合理律师费，仲裁费用和其他费用。</p>
                    <p>（2）向${setting.basic.siteName}公司住所地人民法院起诉。</p>
                    <p>用户同意上述仲裁委 / 法院在仲裁 / 诉讼过程中使用电子邮件向用户注册时预留的电子邮箱送达仲裁 / 诉讼文书。</p>
                    <h5 align="center">十一、其他</h5>
                    <p>1、
                        本协议自用户同意勾选或网上签署并成功注册成为本网站用户之日起生效，除非${setting.basic.siteName}终止本服务协议或者用户丧失本网站用户资格，否则本服务协议始终有效。本服务协议终止并不免除用户根据本服务协议或其他有关协议、规则所应承担的义务和责任。</p>
                    <p>2、 本服务协议部分条款被认定为无效时，不影响本服务协议其他条款的效力。</p>
                    <p>3、 本协议版本于 2015 年 12 月 24 日发布。</p>
                    <p>4、 ${setting.basic.siteName}对本服务协议享有最终的解释权。</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">我同意</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <!--托管注册模态框-->
    <div class="modal fade" id="myModal" style="" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="exampleModalLabel">注册成功</h4>
                </div>
                <div class="modal-body" ty>
                    <p>您需要开通存管账户后才能进行投资，是否立即开通托管账户?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" onclick="toToUc()" class="btn btn-default" data-dismiss="modal">暂不开通
                    </button>
                    <button type="button" onclick="goToSecurity()" class="btn btn-primary">开通托管账户</button>
                </div>
            </div>
        </div>
    </div>
</div>
[/@insert]