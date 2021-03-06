<%@ page import="java.util.ArrayList" %>
<%@ page import="classes.Chats" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.List" %>
<%@include file="user.jsp"%>
<nav class="navbar navbar-expand-lg navbar-dark sticky-top" style="background-color: #17339B;">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>


    <div class="container">
        <div class="navbar-brand">
            <i class="fab fa-asymmetrik fa-2x"></i>
        </div>

        <a class="navbar-brand" href="/">
            ARALASU.KZ
<%--            <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px"--%>
<%--                 width="64" height="64"--%>
<%--                 viewBox="0 0 172 172"--%>
<%--                 style=" fill:#000000;"><g fill="none" fill-rule="nonzero" stroke="none" stroke-width="1" stroke-linecap="butt" stroke-linejoin="miter" stroke-miterlimit="10" stroke-dasharray="" stroke-dashoffset="0" font-family="none" font-weight="none" font-size="none" text-anchor="none" style="mix-blend-mode: normal"><path d="M0,172v-172h172v172z" fill="none"></path><g fill="#ffffff"><path d="M86,9.86816c-3.14941,0 -5.94189,1.76367 -7.28564,4.61914l-65.88574,139.08862c-0.79785,1.66919 -0.67187,3.61133 0.31494,5.17554c0.98682,1.56421 2.6875,2.49853 4.53516,2.49853h8.6294c3.09692,0 5.95239,-1.81616 7.28564,-4.61914l5.58496,-11.77881c0.34643,0.16797 0.72436,0.27295 1.13379,0.27295c1.48022,0 2.6875,-1.19678 2.6875,-2.6875v-5.375c0,-0.07349 -0.03149,-0.13647 -0.04199,-0.20996l3.72681,-7.85254h67.88037l3.72681,7.85254c-0.0105,0.07349 -0.04199,0.13647 -0.04199,0.20996v5.375c0,1.49072 1.20728,2.6875 2.6875,2.6875c0.40942,0 0.78735,-0.10498 1.13379,-0.27295l5.58496,11.77881c1.33325,2.80298 4.18872,4.61914 7.29614,4.61914h19.3689c1.84766,0 3.54834,-0.93433 4.53516,-2.50903c0.98682,-1.55371 1.11279,-3.49585 0.32544,-5.16504l-65.89624,-139.09912c-1.34375,-2.84497 -4.13623,-4.60864 -7.28565,-4.60864zM86,15.24316c0.39893,0 1.75317,0.10498 2.43555,1.53272l65.88574,139.09912h-19.3689c-1.03931,0 -1.99463,-0.59839 -2.42505,-1.53271l-13.10156,-27.64136c-0.88184,-1.86865 -2.79248,-3.07593 -4.8606,-3.07593h-67.88037c-2.06811,0 -3.97876,1.20728 -4.8606,3.07593l-13.10156,27.64136c-0.43042,0.93433 -1.38574,1.53271 -2.41455,1.53271h-8.6294l65.88574,-139.09912c0.68237,-1.41724 2.03662,-1.53272 2.43555,-1.53272zM88.85547,34.33911c-0.34643,0.0105 -0.70337,0.08398 -1.03931,0.25195c-1.34375,0.62988 -1.91064,2.23608 -1.27026,3.57983l2.58252,5.46948c0.46191,0.97632 1.42773,1.54321 2.42505,1.54321c0.38843,0 0.78735,-0.08398 1.15479,-0.25195c1.34375,-0.64038 1.91064,-2.24658 1.28076,-3.57983l-2.59302,-5.47998c-0.47241,-1.00781 -1.49072,-1.5852 -2.54053,-1.53271zM80.625,48.375c-1.03931,0 -1.98413,0.59839 -2.43555,1.54321l-27.99829,59.125c-0.38843,0.82935 -0.33594,1.80566 0.15747,2.58252c0.49341,0.77686 1.34375,1.24927 2.26758,1.24927h56.01758c0.92383,0 1.77417,-0.47241 2.26758,-1.24927c0.49341,-0.77685 0.5459,-1.75317 0.15747,-2.58252l-27.99829,-59.125c-0.45142,-0.94483 -1.39624,-1.54321 -2.43555,-1.54321zM96.59253,50.68457c-0.34643,0.0105 -0.69287,0.09448 -1.02881,0.25195c-1.34375,0.64038 -1.92114,2.23608 -1.28076,3.57983l11.11743,23.46314c0.46191,0.96582 1.41724,1.54321 2.42505,1.54321c0.38843,0 0.78735,-0.09448 1.15478,-0.27295c1.34375,-0.62988 1.91065,-2.22559 1.28076,-3.56934l-11.11743,-23.46313c-0.48291,-1.00781 -1.50122,-1.57471 -2.55103,-1.53272zM80.625,57.34033l23.76758,50.15967h-47.53516zM53.75,134.375c-1.48022,0 -2.6875,1.19678 -2.6875,2.6875v5.375c0,1.49072 1.20728,2.6875 2.6875,2.6875c1.48022,0 2.6875,-1.19678 2.6875,-2.6875v-5.375c0,-1.49072 -1.20728,-2.6875 -2.6875,-2.6875zM67.1875,134.375c-1.48022,0 -2.6875,1.19678 -2.6875,2.6875v5.375c0,1.49072 1.20728,2.6875 2.6875,2.6875c1.48022,0 2.6875,-1.19678 2.6875,-2.6875v-5.375c0,-1.49072 -1.20728,-2.6875 -2.6875,-2.6875zM80.625,134.375c-1.48022,0 -2.6875,1.19678 -2.6875,2.6875v5.375c0,1.49072 1.20728,2.6875 2.6875,2.6875c1.48022,0 2.6875,-1.19678 2.6875,-2.6875v-5.375c0,-1.49072 -1.20728,-2.6875 -2.6875,-2.6875zM94.0625,134.375c-1.48022,0 -2.6875,1.19678 -2.6875,2.6875v5.375c0,1.49072 1.20728,2.6875 2.6875,2.6875c1.48022,0 2.6875,-1.19678 2.6875,-2.6875v-5.375c0,-1.49072 -1.20728,-2.6875 -2.6875,-2.6875zM107.5,134.375c-1.48022,0 -2.6875,1.19678 -2.6875,2.6875v5.375c0,1.49072 1.20728,2.6875 2.6875,2.6875c1.48022,0 2.6875,-1.19678 2.6875,-2.6875v-5.375c0,-1.49072 -1.20728,-2.6875 -2.6875,-2.6875z"></path></g></g></svg>--%>
        </a>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
            <ul class="navbar-nav ml-auto mt-2 mt-lg-0" style="color:white !important;">
                <%
                    if (!ONLINE){
                %>
                <li class="nav-item">
                    <a class="nav-link" href="/faq"><i class="fas fa-question"></i> FAQ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/login"><i class="fas fa-sign-in-alt"></i> Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/register"><i class="fas fa-user-plus"></i> Register</a>
                </li>
                <%
                    }
                    else {
                %>
                <li class="nav-item">
                    <a class="nav-link" href="#"><i class="fas fa-newspaper"></i> Feed</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/friends"><i class="fas fa-user-friends"></i> My friends</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><i class="fas fa-users"></i> Groups</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/myposts"><i class="far fa-comment"></i> My Posts</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/messages"><i class="fas fa-paper-plane"></i>
                        <%
                            Integer not_read = (Integer) request.getSession().getAttribute("NOT_READ");
                            int nr = 0;
                            if (not_read != 0){
                        %>
                        Messages (<%=not_read%>)
                        <%
                            }
                            else {
                        %>
                        Messages
                        <%
                            }
                        %>

                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><i class="fas fa-images"></i> Pictures</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><i class="fas fa-video"></i> Videos</a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>