import Modal from './Modal';
import PostItem from './PostItem';

export default function ModalManager ({
  selectedItem,
  onCloseItem,
  showPostModal,
  onClosePost,
  onSubmitPost
}) {
  return (
    <>
      {selectedItem && <Modal item={selectedItem} onClose={onCloseItem} />}
      {showPostModal && <PostItem onClose={onClosePost} onSubmit={onSubmitPost} />}
    </>
  );
}