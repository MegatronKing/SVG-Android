/*
 * Copyright (C) 2017, Megatron King
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.megatronking.svg.support;

/**
 * Cache the render result.
 *
 * @author Megatron King
 * @since 2016/9/4 15:16
 */

public abstract class CachedRendererStrategy extends RendererStrategy {

    protected float mCachedAlpha;
    protected boolean mCacheDirty;

    public CachedRendererStrategy(SVGRenderer renderer) {
        super(renderer);
    }

    protected void updateCacheStates() {
        // Use shallow copy here and shallow comparison in canReuseCache(),
        // likely hit cache miss more, but practically not much difference.
        mCachedAlpha = mRenderer.mAlpha;
        mCacheDirty = false;
    }

    protected boolean hasTranslucentRoot() {
        return mRenderer.mAlpha < 1.0f;
    }

    protected boolean canReuseCache() {
        return !mCacheDirty && mCachedAlpha == mRenderer.mAlpha;
    }
}
