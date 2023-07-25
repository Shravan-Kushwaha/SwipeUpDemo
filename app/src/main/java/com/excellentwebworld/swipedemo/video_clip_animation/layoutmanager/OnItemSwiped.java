package com.excellentwebworld.swipedemo.video_clip_animation.layoutmanager;

public interface OnItemSwiped {
  void onItemSwiped();
  void onItemSwipedLeft();
  void onItemSwipedRight();
  void onItemSwipedUp();
  void onItemSwipedDown();
  void onItemTransactionAnimation(float dX);
}
