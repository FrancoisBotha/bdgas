<template>
    <div>
        <ul class="nav nav-tabs mt-1">
            <router-link to="/data/list"  active-class="active" exact>
                        <a :class="[$route.fullPath ==='/data/list' ? 'nav-link active' : 'nav-link']">List</a>
            </router-link>
            <router-link to="/data/upload" class="nav-item" active-class="active">
                    <a :class="[$route.fullPath ==='/data/upload' ? 'nav-link active' : 'nav-link']">Upload</a>
            </router-link>
            <router-link to="/data/help" class="nav-item" active-class="active">
                    <a :class="[$route.fullPath ==='/data/help' ? 'nav-link active' : 'nav-link']">Help</a>
            </router-link>
    
        </ul>      
    </div>
</template>