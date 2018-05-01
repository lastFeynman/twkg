package twkg.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twkg.dao.ICoverSongDao;
import twkg.dao.ISongDao;
import twkg.dao.IUserDao;
import twkg.dao.factory.DaoFactory;
import twkg.entity.CoverSong;
import twkg.entity.Song;
import twkg.entity.User;
import twkg.util.ConfigUtil;

/**
 * Servlet implementation class PlayerServlet
 */
@WebServlet("/PlayerServlet")
public class PlayerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlayerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String playerType = request.getParameter("playerType");
		String currentSongStr = null;
		int currentSongId = 0;
		if((currentSongStr = request.getParameter("currentSong"))!=null)
			currentSongId = Integer.parseInt(currentSongStr);
		List<Song> songList = (List<Song>)session.getAttribute("songList");
		if(songList==null) {
			songList = new ArrayList<>();
		}
		List<CoverSong> coverSongList = (List<CoverSong>)session.getAttribute("coverSongList");
		if(coverSongList==null) {
			coverSongList = new ArrayList<>();
		}
		StringBuffer playListStr = new StringBuffer("[");
		
		DaoFactory daoFactory = DaoFactory.getDaoFactory();
		IUserDao userDao = daoFactory.getUserDao();
		ISongDao songDao = daoFactory.getSongDao();
		ICoverSongDao coverSongDao = daoFactory.getCoverSongDao();
		Song currentSong = null;
		CoverSong currentCoverSong = null;
		User user = null;
		if(playerType==null || playerType.equals("song")) {
			currentSong = songDao.findSongBySongId(currentSongId);
			if(currentSongId!=0 && currentSong != null) {
				//播放次数+1
				currentSong.setPlayCount(currentSong.getPlayCount()+1);
				if(!songDao.update(currentSong)) {
					System.out.println("更新失败");
				}
				for(int i=0;songList!=null&&i<=songList.size();i++) {
					if(i==songList.size()) {
						songList.add(currentSong);
						break;
					}
					if(songList.get(i).getSongId() == currentSongId) {
						break;
					}
				}
			}
			for(int i=0;songList!=null&&i<songList.size();i++) {
				if(i!=0) {
					playListStr.append(',');
				}
				playListStr.append("{title:'"+songList.get(i).getSongName()+"',");
				playListStr.append("singer:'"+songList.get(i).getSingerName()+"',");
				playListStr.append("audio:'"+ConfigUtil.SONG_PATH+songList.get(i).getSongId()+".mp3',");
				playListStr.append("thumbnail:'"+ConfigUtil.THUMBNAIL_PATH+songList.get(i).getSongId()+".jpg',");
				playListStr.append("lyric:'"+ConfigUtil.LYRIC_PATH+songList.get(i).getSongId()+".lrc'}");
			}
			session.setAttribute("songList", songList);
		}else {
			currentCoverSong = coverSongDao.findCoverSongByCoverSongId(currentSongId);
			if(currentSongId!=0 && currentCoverSong != null) {
				//播放次数+1
				currentCoverSong.setCoverPlayCount(currentCoverSong.getCoverPlayCount()+1);
				user = userDao.findUserByUserId(currentCoverSong.getUserId());
				user.setUserPopularity(user.getUserPopularity()+1);
				if(!coverSongDao.update(currentCoverSong) || !userDao.update(user)) {
					System.out.println("更新失败");
				}
				for(int i=0;coverSongList!=null&&i<=coverSongList.size();i++) {
					if(i==coverSongList.size()) {
						coverSongList.add(currentCoverSong);
						break;
					}
					if(coverSongList.get(i).getCoverSongId() == currentSongId) {
						break;
					}
				}
			}
			//生成播放列表
			for(int i=0;coverSongList!=null&&i<coverSongList.size();i++) {
				if(i!=0) {
					playListStr.append(',');
				}
				Song coverSongInfo = songDao.findSongBySongId(coverSongList.get(i).getSongId());
				playListStr.append("{title:'"+coverSongInfo.getSongName()+"',");
				playListStr.append("singer:'"+coverSongInfo.getSingerName()+"',");
				playListStr.append("audio:'"+ConfigUtil.COVER_SONG_PATH+coverSongList.get(i).getCoverSongId()+".mp3',");
				playListStr.append("thumbnail:'"+ConfigUtil.THUMBNAIL_PATH+coverSongInfo.getSongId()+".jpg',");
				playListStr.append("lyric:'"+ConfigUtil.LYRIC_PATH+coverSongInfo.getSongId()+".lrc'}");
			}
			session.setAttribute("coverSongList", coverSongList);
		}
		playListStr.append(']');
		session.setAttribute("playListStr", new String(playListStr));
		request.getServletContext().getRequestDispatcher("/player.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
