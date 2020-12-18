package ag.sportradar.avvpldemo


import ag.sportradar.avvplayer.config.AVVConfig
import ag.sportradar.avvplayer.player.controls.AVVControlOverlayDelegate
import ag.sportradar.avvplayer.player.controls.AVVPlayerControlBinding
import ag.sportradar.avvplayer.player.controls.AVVPlayerControlVisibilityV2
import ag.sportradar.avvplayer.player.mediasession.MediaControllable
import ag.sportradar.avvplayer.player.modes.AVVModeControllable
import ag.sportradar.avvplayer.player.ui.view.AVVTimebar
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ui.TimeBar

class MyCustomControls : AVVControlOverlayDelegate {
    override fun onCreateControls(
        parent: ViewGroup,
        controlBinding: AVVPlayerControlBinding,
        mediaControllable: MediaControllable,
        modeControllable: AVVModeControllable,
        config: AVVConfig
    ): View {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_videoplayer_controls, parent, false)


        val avvPlayerControlVisibilityV2 =
            AVVPlayerControlVisibilityV2(
                view as ViewGroup,
                view.findViewById(R.id.controlBackground),
                500,
                3000
            )
        avvPlayerControlVisibilityV2.visibilityChangeListener =
            object : AVVPlayerControlVisibilityV2.ControlVisibilityListener {
                override fun onVisibilityChanged(visible: Boolean) {
                    controlBinding.controlsVisible = visible
                }
            }

        controlBinding.bindPlayPauseButton(
            view.findViewById(R.id.playPauseButton),
            R.drawable.ic_avv_play,
            R.drawable.ic_avv_pause, config.skin
        )

        controlBinding.bindLiveIndicatorView(
            view.findViewById(R.id.liveindicator),
            R.drawable.ic_avv_live_indicator,
            config.skin
        )

        controlBinding.bindChromecastButton(view.findViewById(R.id.chromecast))
        val timeBar = view.findViewById<AVVTimebar>(R.id.timeBar)
        timeBar.addListener(object : TimeBar.OnScrubListener {
            override fun onScrubMove(timeBar: TimeBar, position: Long) {

            }

            override fun onScrubStart(timeBar: TimeBar, position: Long) {
                avvPlayerControlVisibilityV2.show(true)
            }

            override fun onScrubStop(timeBar: TimeBar, position: Long, canceled: Boolean) {
                avvPlayerControlVisibilityV2.hideAfterDelay()
            }

        })
        controlBinding.bindTimeBar(timeBar, config.skin)
        controlBinding.bindDurationView(view.findViewById(R.id.duration), config.skin)
        controlBinding.bindPositionView(view.findViewById(R.id.position), config.skin)
        controlBinding.bindFastForwardButton(view.findViewById(R.id.fastForwardButton), config.skin)
        controlBinding.bindRewindButton(view.findViewById(R.id.rewindButton), config.skin)

        return view
    }

    override fun onKeyDown(
        keyCode: Int,
        event: KeyEvent?,
        mediaControllable: MediaControllable,
        config: AVVConfig?
    ) {

    }
}