/*
 * Copyright (c) 2015-2016 Filippo Engidashet. All Rights Reserved.
 * <p>
 *  Save to the extent permitted by law, you may not use, copy, modify,
 *  distribute or create derivative works of this material or any part
 *  of it without the prior written consent of Filippo Engidashet.
 *  <p>
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 */

package com.lookmytrips.android.controller;



import com.lookmytrips.android.pojo.Feed;

import java.util.List;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @date 1/24/2016
 */
public interface FeedFetchListener {

    void onDeliverAllFlowers(List<Feed> feeds);

    void onDeliverFlower(Feed feed);

    void onHideDialog();
}
