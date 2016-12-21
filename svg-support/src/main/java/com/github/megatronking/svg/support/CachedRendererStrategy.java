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
